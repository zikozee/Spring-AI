package com.zee.aidocs.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 22 Jul, 2024
 */

@Slf4j
@Component
public class ReferenceDocsLoader {

    private final JdbcClient jdbcClient;
    private final VectorStore vectorStore;
    private final Resource pdfResource;

    public ReferenceDocsLoader(JdbcClient jdbcClient, VectorStore vectorStore,
                               @Value("classpath:docs/spring-boot-reference.pdf") Resource pdfResource) {
        this.jdbcClient = jdbcClient;
        this.vectorStore = vectorStore;
        this.pdfResource = pdfResource;
    }

    @PostConstruct
    public void init() {
        Integer count = jdbcClient.sql("select count(*) from vector_store")
                .query(Integer.class)
                .single();

        log.info("Current count of the vector store: {}", count);
        if(count == 0) {
            log.info("Loading Spring Boot reference PDF into vector store...");
            PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
                    .withPageExtractedTextFormatter(new ExtractedTextFormatter.Builder().withNumberOfBottomTextLinesToDelete(0)
                            .withNumberOfTopPagesToSkipBeforeDelete(0)
                            .build())
                    .withPagesPerDocument(1)
                    .build();

            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource, config);
            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
            vectorStore.accept(tokenTextSplitter.apply(pdfReader.get()));

            log.info("Application is ready");

        }
    }
}
