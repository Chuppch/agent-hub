package com.chuppch.domain.agent.service.armory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.chuppch.domain.agent.adapter.repository.IAgentRepository;
import com.chuppch.domain.agent.model.entity.ArmoryCommandEntity;
import com.chuppch.domain.agent.model.valobj.AiAgentClientFlowConfigVO;
import com.chuppch.domain.agent.model.valobj.AiAgentVO;
import com.chuppch.domain.agent.model.valobj.enums.AiAgentEnumVO;
import com.chuppch.domain.agent.service.IArmoryService;
import com.chuppch.domain.agent.service.armory.node.factory.DefaultArmoryStrategyFactory;
import jakarta.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chuppch
 * @description
 * @create 2025/12/16
 */
public class ArmoryService implements IArmoryService {

    @Resource
    private IAgentRepository repository;

    @Resource
    private DefaultArmoryStrategyFactory defaultArmoryStrategyFactory;

    @Override
    public List<AiAgentVO> acceptArmoryAllAvailableAgents() {
        return List.of();
    }

    @Override
    public void acceptArmoryAgent(String agentId) {
        List<AiAgentClientFlowConfigVO> aiAgentClientFlowConfigVOS = repository.queryAiAgentClientsByAgentId(agentId);
        if (aiAgentClientFlowConfigVOS.isEmpty()) {
            return;
        }

        // 获取命令ID列表
        List<String> commadIdList = aiAgentClientFlowConfigVOS.stream()
                .map(AiAgentClientFlowConfigVO::getClientId)
                .collect(Collectors.toList());

        // 装配智能体
        try {
            StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> armoryStrategyHandler =
                    defaultArmoryStrategyFactory.armoryStrategyHandler();

            armoryStrategyHandler.apply(
                    ArmoryCommandEntity.builder()
                            .commandType(AiAgentEnumVO.AI_CLIENT.getCode())
                            .commandIdList(commadIdList)
                            .build(),
                    new DefaultArmoryStrategyFactory.DynamicContext());
        } catch (Exception e) {
            throw new RuntimeException("装配智能体失败",e);
        }
    }

    @Override
    public List<AiAgentVO> queryAvailableAgents() {
        return List.of();
    }

    @Override
    public void acceptArmoryAgentClientModelApi(String agentId) {

    }
}