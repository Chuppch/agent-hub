package com.chuppch.domain.agent.service.excutor.flow.step;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.chuppch.domain.agent.model.entity.ExecuteCommandEntity;
import com.chuppch.domain.agent.service.excutor.flow.step.factory.DefaultFlowAgentExecuteStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author chuppch
 * @description
 * @create 2026/1/4
 */
@Slf4j
@Service
public class Step2PlanningNode extends AbstractExecuteSupport{
    @Override
    protected String doApply(ExecuteCommandEntity executeCommandEntity, DefaultFlowAgentExecuteStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("\n--- 步骤2: 执行步骤规划 ---");


        return "";
    }

    @Override
    public StrategyHandler<ExecuteCommandEntity, DefaultFlowAgentExecuteStrategyFactory.DynamicContext, String> get(ExecuteCommandEntity executeCommandEntity, DefaultFlowAgentExecuteStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return null;
    }
}