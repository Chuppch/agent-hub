package com.chuppch.infrastructure.adapter.repository;

import com.chuppch.domain.agent.adapter.repository.IAgentRepository;
import com.chuppch.domain.agent.model.valobj.AiClientApiVO;
import com.chuppch.domain.agent.model.valobj.AiClientModelVO;
import com.chuppch.infrastructure.dao.*;
import com.chuppch.infrastructure.dao.po.AiClientApi;
import com.chuppch.infrastructure.dao.po.AiClientConfig;
import com.chuppch.infrastructure.dao.po.AiClientModel;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static com.chuppch.domain.agent.model.valobj.enums.AiAgentEnumVO.AI_CLIENT;
import static com.chuppch.domain.agent.model.valobj.enums.AiAgentEnumVO.AI_CLIENT_MODEL;

/**
 * @author chuppch
 * @description
 * @create 2025/12/18
 */
public class AgentRepository implements IAgentRepository {

    @Resource
    private IAiAgentDao aiAgentDao;

    @Resource
    private IAiAgentFlowConfigDao aiAgentFlowConfigDao;

    @Resource
    private IAiAgentTaskScheduleDao aiAgentTaskScheduleDao;

    @Resource
    private IAiClientAdvisorDao aiClientAdvisorDao;

    @Resource
    private IAiClientApiDao aiClientApiDao;

    @Resource
    private IAiClientConfigDao aiClientConfigDao;

    @Resource
    private IAiClientDao aiClientDao;

    @Resource
    private IAiClientModelDao aiClientModelDao;

    @Resource
    private IAiClientRagOrderDao aiClientRagOrderDao;

    @Resource
    private IAiClientSystemPromptDao aiClientSystemPromptDao;

    @Resource
    private IAiClientToolMcpDao aiClientToolMcpDao;

    // ============ AiClientApiLoadDataStrategy - 数据库操作 ============


    // ============ AiClientLoadDataStrategy - 数据库操作 ============
    
    @Override
    public List<AiClientApiVO> queryAiClientApiVOListByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientApiVO> result = new ArrayList<>();

        for (String clientId : clientIdList) {
            // 1. 通过clientId查询关联的modelId
            List<AiClientConfig> configs = aiClientConfigDao.queryBySourceTypeAndId(AI_CLIENT.getCode(), clientId);

            for (AiClientConfig config : configs) {
                // 判断当前配置对应的目标类型是否为“模型”（AI_CLIENT_MODEL），且配置状态为启用（status为1）
                if (AI_CLIENT_MODEL.getCode().equals(config.getTargetType()) && config.getStatus() == 1) {
                    String modelId = config.getTargetId();

                    // 2. 通过modelId查询模型配置，获取apiId
                    AiClientModel model = aiClientModelDao.queryByModelId(modelId);
                    // 判断是否查到API配置，并且API配置处于启用状态（status==1 表示有效/启用）
                    if (model != null && model.getStatus() == 1) {
                        String apiId = model.getApiId();

                        // 3. 通过apiId查询API配置信息
                        AiClientApi apiConfig = aiClientApiDao.queryByApiId(apiId);
                        // 判断API配置是否不为空且状态为启用
                        if (apiConfig != null && apiConfig.getStatus() == 1) {
                            // 4. 转换为VO对象
                            AiClientApiVO apiVO = AiClientApiVO.builder()
                                    .apiId(apiConfig.getApiId())
                                    .baseUrl(apiConfig.getBaseUrl())
                                    .apiKey(apiConfig.getApiKey())
                                    .completionsPath(apiConfig.getCompletionsPath())
                                    .embeddingsPath(apiConfig.getEmbeddingsPath())
                                    .build();

                            // 避免重复添加相同的API配置
                            if (result.stream().noneMatch(vo -> vo.getApiId().equals(apiVO.getApiId()))) {
                                result.add(apiVO);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }




}