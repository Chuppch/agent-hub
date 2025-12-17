package com.chuppch.domain.agent.service.armory.business.data.impl;

import com.chuppch.domain.agent.adapter.repository.IAgentRepository;
import com.chuppch.domain.agent.model.entity.ArmoryCommandEntity;
import com.chuppch.domain.agent.model.valobj.AiClientApiVO;
import com.chuppch.domain.agent.model.valobj.AiClientModelVO;
import com.chuppch.domain.agent.service.armory.business.data.ILoadDataStrategy;
import com.chuppch.domain.agent.service.armory.node.factory.DefaultArmoryStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chuppch
 * @description
 * @create 2025/12/16
 */
@Slf4j
@Service("aiClientModelLoadDataStrategy")
public class AiClientModelLoadDataStrategy implements ILoadDataStrategy {

    // 仓储
    @Resource
    private IAgentRepository repository;

    // 线程池
    @Resource
    protected ThreadPoolExecutor threadPoolExecutor;

    // 数据加载
    @Override
    public void loadData(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) {
        List<String> modelIdList = armoryCommandEntity.getCommandIdList();

        CompletableFuture<List<AiClientApiVO>> aiClientApiListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_api) {}", modelIdList);
            return repository.queryAiClientApiVOListByModelIds(modelIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientModelVO>> aiClientModelListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_model) {}", modelIdList);
            return repository.AiClientModelVOByModelIds(modelIdList);
        }, threadPoolExecutor);

    }
}