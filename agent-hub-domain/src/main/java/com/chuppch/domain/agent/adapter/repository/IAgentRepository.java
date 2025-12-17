package com.chuppch.domain.agent.adapter.repository;

import com.chuppch.domain.agent.model.valobj.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author chuppch
 * @description
 * @create 2025/12/17
 */
public interface IAgentRepository {

    // ============ AiClientApiLoadDataStrategy - 数据库操作 ============

    List<AiClientApiVO> queryAiClientApiVOListByApiIds(List<String> apiIdList);

    // ============ AiClientLoadDataStrategy - 数据库操作 ============

    List<AiClientApiVO> queryAiClientApiVOListByClientIds(List<String> clientIdList);

    List<AiClientModelVO> AiClientModelVOByClientIds(List<String> clientIdList);

    List<AiClientToolMcpVO> AiClientToolMcpVOByClientIds(List<String> clientIdList);

    Map<String, AiClientSystemPromptVO> queryAiClientSystemPromptMapByClientIds(List<String> clientIdList);

    List<AiClientAdvisorVO> AiClientAdvisorVOByClientIds(List<String> clientIdList);

    List<AiClientVO> AiClientVOByClientIds(List<String> clientIdList);

    // ============ AiClientModelLoadDataStrategy - 数据库操作 ============

    List<AiClientApiVO> queryAiClientApiVOListByModelIds(List<String> modelIdList);

    List<AiClientModelVO> AiClientModelVOByModelIds(List<String> modelIdList);

}
