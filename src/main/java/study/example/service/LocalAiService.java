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
    private String systemPrompt = " You create a short diary title.\n" +
            "Read the diary text and generate ONE title.\n" +
            "Rules:\n" +
            "- Output title only.\n" +
            "- No explanation.\n" +
            "- No quotes.\n" +
            "- Maximum 15 words.\n" +
            "- Use the same language as the diary.\n" +
            "- Make it natural and emotional.";

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
                    .addUserMessage("\n diary:\n" + userPrompt)
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
                .ntokens(2048) //By default, 256
                .temperature(0.0f) //By default, 0.0f
                .onTokenWithTimings((s, aFloat) -> {}) //By default, (s, aFloat) -> {}, nothing
                .generate();

        return r.responseText;
    }
}
