package com.springai.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 10 Jul, 2024
 */

@Slf4j
@Configuration
public class RagConfiguration {

    @Value("classpath:docs/olympics-faq.txt")
    private Resource faq;

    @Value("vectorstore.json")
    private String vectorStoreName;

    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(embeddingModel);
        File vectorStoreFile = getVectorStoreFile();
        if (vectorStoreFile.exists()) {
            log.info("Vector file exists. path='{}'", vectorStoreFile.getAbsolutePath());
            simpleVectorStore.load(vectorStoreFile);
        }else {
            log.info("Vector file does not exist, loading documents...");
            TextReader textReader = new TextReader(faq);
            textReader.getCustomMetadata().put("filename", "olympics-faq.txt");
            List<Document> documents = textReader.get();
            TokenTextSplitter textSplitter = new TokenTextSplitter();
            List<Document> splitDocuments = textSplitter.apply(documents);

            simpleVectorStore.add(splitDocuments);
            simpleVectorStore.save(vectorStoreFile);
        }

        return simpleVectorStore;
    }

    private File getVectorStoreFile(){
        Path path = Paths.get("spring-ai-intro", "src", "main", "resources", "data");
        String absolutePath = path.toFile().getAbsolutePath() +  "/" +  vectorStoreName;
        return new File(absolutePath);
    }
}
