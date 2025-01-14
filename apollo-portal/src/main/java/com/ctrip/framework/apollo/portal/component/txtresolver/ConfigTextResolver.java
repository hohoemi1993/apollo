package com.ctrip.framework.apollo.portal.component.txtresolver;

import com.ctrip.framework.apollo.common.dto.ItemChangeSets;
import com.ctrip.framework.apollo.common.dto.ItemDTO;

import java.util.List;

/**
 * users can modify config in text mode.so need resolve text.
 * 配置文本解析器接口
 */
public interface ConfigTextResolver {

  ItemChangeSets resolve(long namespaceId, String configText, List<ItemDTO> baseItems);

}
