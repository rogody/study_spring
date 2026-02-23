package study.example.service;

import com.github.tjake.jlama.model.AbstractModel;
import com.github.tjake.jlama.model.functions.Generator;
import com.github.tjake.jlama.safetensors.prompt.PromptContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.example.ai.LoadLocalAI;

import java.util.UUID;

@Component
public class LocalAiService implements AiService{
    //llm의 system prompt
    private String systemPrompt =
            "역할: 일기 제목 생성기\n" +
                    "\n" +
                    "입력된 일기를 읽고 제목 하나만 생성하세요.\n" +
                    "\n" +
                    "출력 형식:\n" +
                    "- 제목만 출력\n" +
                    "- 다른 문장 절대 금지\n" +
                    "- 한 줄만 출력\n" +
                    "- 따옴표 금지\n" +
                    "- 최대 15단어\n" +
                    "- 입력과 같은 언어 사용";

    private final AbstractModel LLM;

    @Autowired
    public LocalAiService(LoadLocalAI loadLocalAI) {
        LLM = loadLocalAI.getLocalAi();

    }

    public PromptContext buildPrompt(String userPrompt) {
        PromptContext ctx;
        if (LLM.promptSupport().isPresent()) {
            ctx = LLM.promptSupport()
                    .get()
                    .builder()
                    .addSystemMessage(systemPrompt)
                    .addUserMessage(("\n\n일기:\n" + userPrompt))
                    .build();
        } else {
            ctx = PromptContext.of(systemPrompt+userPrompt);
        }
        return ctx;

    }

    @Override
    public String summarize(String content) {
        return generate(this.buildPrompt(content));
    }

    public String generate(PromptContext ctx){
        Generator.Response r = LLM.generateBuilder()
                .session(UUID.randomUUID()) //By default, UUID.randomUUID()
                .promptContext(ctx) // Required or use prompt(String text)
                .ntokens(256) //By default, 256
                .temperature(0.0f) //By default, 0.0f
                .onTokenWithTimings((s, aFloat) -> {}) //By default, (s, aFloat) -> {}, nothing
                .generate();
        return r.responseText;
    }
}
