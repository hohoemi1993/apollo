package com.ctrip.framework.apollo.portal.listener;

import com.ctrip.framework.apollo.common.dto.AppDTO;
import com.ctrip.framework.apollo.common.dto.AppNamespaceDTO;
import com.ctrip.framework.apollo.common.utils.BeanUtils;
import com.ctrip.framework.apollo.core.enums.Env;
import com.ctrip.framework.apollo.portal.api.AdminServiceAPI;
import com.ctrip.framework.apollo.portal.component.PortalSettings;
import com.ctrip.framework.apollo.tracer.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreationListener {

  private static Logger logger = LoggerFactory.getLogger(CreationListener.class);

  private final PortalSettings portalSettings;
  private final AdminServiceAPI.AppAPI appAPI;
  private final AdminServiceAPI.NamespaceAPI namespaceAPI;

  public CreationListener(
      final PortalSettings portalSettings,
      final AdminServiceAPI.AppAPI appAPI,
      final AdminServiceAPI.NamespaceAPI namespaceAPI) {
    this.portalSettings = portalSettings;
    this.appAPI = appAPI;
    this.namespaceAPI = namespaceAPI;
  }

  @EventListener
  public void onAppCreationEvent(AppCreationEvent event) {
    // 对象转化
    AppDTO appDTO = BeanUtils.transform(AppDTO.class, event.getApp());
    // 获取env
    List<Env> envs = portalSettings.getActiveEnvs();
    for (Env env : envs) {
      try {
        // 调用 AppAPI#createApp(Env, AppDTO) 方法，调用对应的 Admin Service 的 API ，创建 App 对象，从而同步 App 到 Config DB。
        appAPI.createApp(env, appDTO);
      } catch (Throwable e) {
        logger.error("Create app failed. appId = {}, env = {})", appDTO.getAppId(), env, e);
        Tracer.logError(String.format("Create app failed. appId = %s, env = %s", appDTO.getAppId(), env), e);
      }
    }
  }

  @EventListener
  public void onAppNamespaceCreationEvent(AppNamespaceCreationEvent event) {
    AppNamespaceDTO appNamespace = BeanUtils.transform(AppNamespaceDTO.class, event.getAppNamespace());
    List<Env> envs = portalSettings.getActiveEnvs();
    for (Env env : envs) {
      try {
        namespaceAPI.createAppNamespace(env, appNamespace);
      } catch (Throwable e) {
        logger.error("Create appNamespace failed. appId = {}, env = {}", appNamespace.getAppId(), env, e);
        Tracer.logError(String.format("Create appNamespace failed. appId = %s, env = %s", appNamespace.getAppId(), env), e);
      }
    }
  }

}
