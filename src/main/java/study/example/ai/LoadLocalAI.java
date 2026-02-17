package study.example.ai;

import com.github.tjake.jlama.model.AbstractModel;
import com.github.tjake.jlama.model.ModelSupport;
import com.github.tjake.jlama.safetensors.DType;
import com.github.tjake.jlama.util.Downloader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class LoadLocalAI {

    private AbstractModel llm;

    @PostConstruct
    public void init() {
        String model = "tjake/Llama-3.2-3B-Instruct-JQ4";
        String workingDirectory = "./aimodels";

        // Downloads the model or just returns the local path if it's already downloaded
        try {
            System.out.println("loading hugging face model");
            File localModelPath = new Downloader(workingDirectory, model).huggingFaceModel();
            // Loads the quantized model and specified use of quantized memory
            llm = ModelSupport.loadModel(localModelPath, DType.F32, DType.I8);
            System.out.println("model loaded");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("failed to load model");
        }
    }

    public AbstractModel getLocalAi() {
        return llm;
    }
}
