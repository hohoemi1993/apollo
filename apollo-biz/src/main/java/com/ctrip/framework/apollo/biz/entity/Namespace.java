package com.ctrip.framework.apollo.biz.entity;

import com.ctrip.framework.apollo.common.entity.BaseEntity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Namespace 类型有三种：
 *
 * 私有类型：私有类型的 Namespace 具有 private 权限。
 * 公共类型：公共类型的 Namespace 具有 public 权限。
 *          公共类型的 Namespace 相当于游离于应用之外的配置，且通过 Namespace 的名称去标识公共 Namespace ，
 *          所以公共的 Namespace 的名称必须全局唯一。
 * 关联类型：关联类型又可称为继承类型，关联类型具有 private 权限。
 *          关联类型的Namespace 继承于公共类型的Namespace，
 *          用于覆盖公共 Namespace 的某些配置。
 */
@Entity
@Table(name = "Namespace")
@SQLDelete(sql = "Update Namespace set isDeleted = 1 where id = ?")
@Where(clause = "isDeleted = 0")
public class Namespace extends BaseEntity {

  @Column(name = "appId", nullable = false)
  private String appId;

  @Column(name = "ClusterName", nullable = false)
  private String clusterName;

  @Column(name = "NamespaceName", nullable = false)
  private String namespaceName;

  public Namespace(){

  }

  public Namespace(String appId, String clusterName, String namespaceName) {
    this.appId = appId;
    this.clusterName = clusterName;
    this.namespaceName = namespaceName;
  }

  public String getAppId() {
    return appId;
  }

  public String getClusterName() {
    return clusterName;
  }

  public String getNamespaceName() {
    return namespaceName;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public void setNamespaceName(String namespaceName) {
    this.namespaceName = namespaceName;
  }

  public String toString() {
    return toStringHelper().add("appId", appId).add("clusterName", clusterName)
        .add("namespaceName", namespaceName).toString();
  }
}
