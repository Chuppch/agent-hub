package com.chuppch.config;

import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author chuppch
 * @description
 * @create 2025/12/16
 */
@Configuration
public class AiAgentConfig {

    // 初始化向量存储
    @Bean("vectorStore")
    public PgVectorStore pgVectorStore(@Value("${spring.ai.openai.base-url}") String baseUrl,
                                       @Value("${spring.ai.openai.api-key}") String apiKey,
                                       @Value("${spring.ai.openai.completions-path:v1/chat/completions}") String completionsPath,
                                       @Value("${spring.ai.openai.embeddings-path:v1/embeddings}") String embeddingsPath,
                                       @Qualifier("pgVectorJdbcTemplate") JdbcTemplate jdbcTemplate) {
        // 初始化 OpenAI API - 配置 OpenAI API 的 URL、API Key 和路径
        // baseUrl + embeddingsPath = https://apis.itedus.cn/v1/embeddings
        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .completionsPath(completionsPath)
                .embeddingsPath(embeddingsPath)
                .build();

        // 初始化 OpenAiEmbeddingModel - 实现数据的向量化
        OpenAiEmbeddingModel embeddingModel = new OpenAiEmbeddingModel(openAiApi);
        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .vectorTableName("vector_store_openai")
                .build();
    }

    // 初始化文本分割器
    @Bean("tokenTextSplitter")
    public TokenTextSplitter tokenTextSplitter() {
        // todo 后续需要补充更详细的参数
        return new TokenTextSplitter();
    }

}