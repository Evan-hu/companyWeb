package com.ga.erp.biz;

import org.apache.click.Page;
import org.apache.click.util.Bindable;

import com.ga.click.control.tree.PanelTreeMenuControl;
import com.ga.click.control.tree.TopMenuControl;
import com.ga.click.control.tree.TreeNode;

/**
 * 菜单解析,根据登录用户和权限设置构建菜单
 * 格式为固定的顶部菜单结合左侧菜单,一级菜单为顶部导航,二三级菜单为左侧面板树
 * @author Administrator
 *
 */
public class Menu extends Page {
	
  private static final long serialVersionUID = 1L;
  
  @Bindable
  private PanelTreeMenuControl panelTree;
  private TreeNode treeNode;
  private Long defaultMenuID = 1L;

  /**
   * 构建时初始化菜单结构，并将第一个菜单设为模式显示菜单
   */
  public Menu() {
	  
//    if (this.getContext().getRequest().getSession(false) != null) {
//      UserACL acl = (UserACL) this.getContext().getRequest().getSession(false).getAttribute("userAcl");
//      if (acl == null) {
//        throw new BizException("未登录");//未登陆
//      }
//      treeNode = acl.getMyRootNode();
//      if (treeNode != null && treeNode.getChildNodes() != null) {
//        this.defaultMenuID = treeNode.getChildNodes().get(0).getNodeID();
//      }
//    }
    
    treeNode = this.initTreeNode();
    if (treeNode != null && treeNode.getChildNodes() != null) {
      this.defaultMenuID = treeNode.getChildNodes().get(0).getNodeID();
    }
  }

  /**
   * 初始化菜单
   * @return
   */
  private TreeNode initTreeNode() {
    
    TreeNode treeNode = new TreeNode(0L,"根节点","");
    
    treeNode.addChildNode(new TreeNode(11L,"统计分析管理","/menu.htm"));
    treeNode.addChildNode(11L,new TreeNode(1101L,"会员统计",""));
//    treeNode.addChildNode(1101L,new TreeNode(11011L,"会员分析","/supplier/supplier_main.htm"));
    treeNode.addChildNode(1101L,new TreeNode(11012L,"会员忠诚度分析","/member/vipLoyaltyAn_main.htm"));
    treeNode.addChildNode(1101L,new TreeNode(11013L,"会员消费群体分析","/statistic/member/MemGroup.jsp"));
    treeNode.addChildNode(11L,new TreeNode(1102L,"销售统计",""));
//    treeNode.addChildNode(1102L,new TreeNode(11021L,"销售分析","/supplier/supplier_main.htm"));
    treeNode.addChildNode(11L,new TreeNode(1103L,"门店统计",""));
    treeNode.addChildNode(1103L,new TreeNode(11031L,"单店分析","/statistic/store/houseCnt.jsp"));
    treeNode.addChildNode(1103L,new TreeNode(11032L,"订单分析","/statistic/store/order.jsp"));
    treeNode.addChildNode(1103L,new TreeNode(11033L,"热销商品","/statistic/store/HotComm.jsp"));
    treeNode.addChildNode(1103L,new TreeNode(11034L,"销售ABC","/abc/abc_mainpage.htm"));
    
    treeNode.addChildNode(new TreeNode(4L,"门店管理","/menu.htm"));
    treeNode.addChildNode(4L,new TreeNode(41L,"门店管理",""));
    treeNode.addChildNode(41L,new TreeNode(411L,"门店信息","/store/store_main.htm"));
    treeNode.addChildNode(41L,new TreeNode(412L,"POS机信息","/store/storepos/pos_main.htm"));
//    treeNode.addChildNode(41L,new TreeNode(413L,"门店费用单","/costorder/costorder_main.htm?type=3"));
    treeNode.addChildNode(4L,new TreeNode(42L,"门店订单管理",""));
    treeNode.addChildNode(42L,new TreeNode(421L,"销售订单","/store/storesalesorder/salesorder_main.htm"));
    treeNode.addChildNode(42L,new TreeNode(422L,"退货订单", "/store/returnOrder_main.htm"));
//    treeNode.addChildNode(42L,new TreeNode(422L,"门店销售订单明细","/store/storesalesdetail/salesdetail_main.htm"));
    treeNode.addChildNode(4L,new TreeNode(43L,"门店异常告警",""));
    treeNode.addChildNode(43L,new TreeNode(431L,"门店缺货告警", "/store/storealert_main.htm?type=2"));
    treeNode.addChildNode(43L,new TreeNode(432L,"门店负库存商品", "/store/storealert_main.htm?type=1"));
    treeNode.addChildNode(4L,new TreeNode(44L,"门店日志管理",""));
    treeNode.addChildNode(44L,new TreeNode(441L,"门店钱包日志","/store/storewalletlog/walletlog_main.htm"));
    treeNode.addChildNode(4L,new TreeNode(45L,"站内信管理",""));
    treeNode.addChildNode(45L,new TreeNode(451L,"门店站内信","/content/stationmsg_main.htm?type=2"));
    treeNode.addChildNode(4L,new TreeNode(46L,"门店商品价格调整",""));
    treeNode.addChildNode(46L,new TreeNode(461L,"价格调整单","/modifyprice/bill_main.htm?objType=3"));
//    treeNode.addChildNode(451L,new TreeNode(4511L,"发送日志","/member/msgsend_main.htm"));
    
    treeNode.addChildNode(new TreeNode(7L,"门店单据管理","/menu.htm"));
    treeNode.addChildNode(7L,new TreeNode(71L,"门店要货",""));
    treeNode.addChildNode(71L,new TreeNode(712L,"门店要货申请", "/store/storeorder_main.htm?type=4"));
    treeNode.addChildNode(71L,new TreeNode(711L,"店间直调申请", "/store/storeorder_main.htm?type=1"));
    treeNode.addChildNode(7L,new TreeNode(72L,"门店发货",""));
    treeNode.addChildNode(72L,new TreeNode(721L,"门店直调发货", "/store/storeorder_main.htm?type=4"));
    treeNode.addChildNode(7L,new TreeNode(73L,"门店收货",""));
    treeNode.addChildNode(73L,new TreeNode(731L,"店间直调收货", "/store/storeorder_main.htm?type=3"));
    treeNode.addChildNode(73L,new TreeNode(732L,"门店要货收货", "/store/storeorder_main.htm?type=6"));
    treeNode.addChildNode(73L,new TreeNode(733L,"店间直调退货收货", "/store/storeorder_main.htm?type=10"));
    treeNode.addChildNode(7L,new TreeNode(74L,"门店退货",""));
    treeNode.addChildNode(74L,new TreeNode(741L,"采购退货申请", "/store/storeorder_main.htm?type=7"));
    treeNode.addChildNode(74L,new TreeNode(742L,"直调退货申请", "/store/storeorder_main.htm?type=9"));
//    treeNode.addChildNode(7L,new TreeNode(75L,"采购商品",""));
//    treeNode.addChildNode(75L,new TreeNode(751L,"采购目录商品","/store/storecataloguecomm_main.htm"));
    
    treeNode.addChildNode(new TreeNode(13L,"盘点管理","/menu.htm"));
    treeNode.addChildNode(13L,new TreeNode(131L,"仓库盘点管理",""));
    treeNode.addChildNode(131L,new TreeNode(1311L,"盘点批次管理","/stock/batch_main.htm?type=1"));
    treeNode.addChildNode(131L,new TreeNode(1312L,"盘点单管理","/stock/bill_main.htm?type=1"));
    treeNode.addChildNode(131L,new TreeNode(1313L,"盘点差异单","/stock/diffbill_main.htm?type=1"));
    
    treeNode.addChildNode(13L,new TreeNode(171L,"门店盘点管理",""));
    treeNode.addChildNode(171L,new TreeNode(1711L,"盘点批次管理","/stock/batch_main.htm?type=2"));
    treeNode.addChildNode(171L,new TreeNode(1712L,"盘点单管理","/stock/bill_main.htm?type=2"));
    treeNode.addChildNode(171L,new TreeNode(1713L,"盘点差异单","/stock/diffbill_main.htm?type=2"));
    
    treeNode.addChildNode(new TreeNode(8L,"仓库管理","/menu.htm"));
    treeNode.addChildNode(8L,new TreeNode(81L,"仓库管理",""));
    treeNode.addChildNode(81L,new TreeNode(811L,"仓库信息","/stock/stock_main.htm"));
    treeNode.addChildNode(81L,new TreeNode(812L,"货架信息","/stock/shelf_main.htm"));
    treeNode.addChildNode(81L,new TreeNode(813L,"库存商品信息","/stock/comm_main.htm"));
    treeNode.addChildNode(8L,new TreeNode(82L,"仓库异常告警",""));
    treeNode.addChildNode(82L,new TreeNode(821L,"库存告警","/stock/stockalert_main.htm?type=2"));
    treeNode.addChildNode(82L,new TreeNode(822L,"负库存商品","/stock/stockalert_main.htm?type=1"));
    treeNode.addChildNode(8L,new TreeNode(83L,"仓库商品价格调整",""));
    treeNode.addChildNode(83L,new TreeNode(831L,"仓库价格调整单","/modifyprice/bill_main.htm?objType=2"));
    
    treeNode.addChildNode(new TreeNode(14L,"DC单据管理","/menu.htm"));
    treeNode.addChildNode(14L,new TreeNode(141L,"DC要货",""));
    treeNode.addChildNode(141L,new TreeNode(1411L,"采购申请单","/purchase/purOrder_main.htm?orderType=1"));
    
    treeNode.addChildNode(14L,new TreeNode(143L,"DC发货",""));
    treeNode.addChildNode(143L,new TreeNode(1421L,"门店要货单","/store/storeorder_main.htm?type=2"));
    treeNode.addChildNode(143L,new TreeNode(1431L,"大客户要货单","/purchase/purOrder_main.htm?orderType=6"));
    treeNode.addChildNode(143L,new TreeNode(1432L,"大客户销售单","/purchase/purOrder_main.htm?orderType=7"));
    
    treeNode.addChildNode(14L,new TreeNode(142L,"DC收货",""));
    treeNode.addChildNode(142L,new TreeNode(1412L,"采购收货单","/purchase/purOrder_main.htm?orderType=3"));
    treeNode.addChildNode(142L,new TreeNode(1422L,"门店退货单","/store/storeorder_main.htm?type=3"));
    treeNode.addChildNode(142L,new TreeNode(1433L,"大客户退货单","/purchase/purOrder_main.htm?orderType=8"));
    
//    treeNode.addChildNode(141L,new TreeNode (1411L,"采购清单","/purchase/purList_main.htm"));
    treeNode.addChildNode(14L,new TreeNode(144L,"DC退货",""));
    treeNode.addChildNode(144L,new TreeNode(1413L,"采购退货单","/purchase/purOrder_main.htm?orderType=5"));

    
    treeNode.addChildNode(new TreeNode(16L,"供应商单据管理","/menu.htm"));
    treeNode.addChildNode(16L,new TreeNode(161L,"供应商发货",""));
    treeNode.addChildNode(161L,new TreeNode(1611L,"DC要货单","/purchase/purOrder_main.htm?orderType=1"));
    
    treeNode.addChildNode(new TreeNode(6L,"供应商管理","/menu.htm"));
    treeNode.addChildNode(6L,new TreeNode(61L,"供应商管理",""));
    treeNode.addChildNode(61L,new TreeNode(611L,"基础信息维护","/supplier/supplier_main.htm?suppliertype=1"));
//    treeNode.addChildNode(61L,new TreeNode(612L,"供应商费用单","/costorder/costorder_main.htm?type=1&&suppliertype=1"));
//    treeNode.addChildNode(61L,new TreeNode(612L,"供应商废除","/supplier/supplier_main.htm"));
//    treeNode.addChildNode(61L,new TreeNode(613L,"供应商供货","/supplier/supplier_main.htm"));
//    treeNode.addChildNode(6L,new TreeNode(62L,"供货管理",""));
//    treeNode.addChildNode(62L,new TreeNode(621L,"供应商品信息","/supplier/commodity/suppliercommodity_main.htm?suppliertype=1"));
//    treeNode.addChildNode(6L,new TreeNode(63L,"合同管理",""));
//    treeNode.addChildNode(63L,new TreeNode(631L,"合同信息","/supplier/contract_main.htm?suppliertype=1"));
//    treeNode.addChildNode(6L,new TreeNode(64L,"站内信管理",""));
    treeNode.addChildNode(61L,new TreeNode(612L,"供应商站内信","/content/stationmsg_main.htm?type=3&&suppliertype=1"));
//    treeNode.addChildNode(64L,new TreeNode(642L,"发送日志","/member/msgsend_main.htm"));
    
    treeNode.addChildNode(new TreeNode(15L,"大客户管理","/menu.htm"));
    treeNode.addChildNode(15L,new TreeNode(151L,"大客户管理",""));
    treeNode.addChildNode(151L,new TreeNode(1511L,"基础信息维护","/supplier/supplier_main.htm?suppliertype=2"));
//    treeNode.addChildNode(151L,new TreeNode(1512L,"大客户费用单","/costorder/costorder_main.htm?type=1&&suppliertype=2"));
//    treeNode.addChildNode(15L,new TreeNode(153L,"合同管理",""));
//    treeNode.addChildNode(153L,new TreeNode(1531L,"合同信息","/supplier/contract_main.htm?suppliertype=2"));
//    treeNode.addChildNode(15L,new TreeNode(154L,"站内信管理",""));
    treeNode.addChildNode(151L,new TreeNode(1512L,"大客户站内信","/content/stationmsg_main.htm?type=3&&suppliertype=2"));
//    treeNode.addChildNode(64L,new TreeNode(642L,"发送日志","/member/msgsend_main.htm"));
//    treeNode.addChildNode(6L,new TreeNode(65L,"供应商商品价格调整",""));
    treeNode.addChildNode(61L,new TreeNode(613L,"商品调价申请","/modifyprice/bill_main.htm?objType=1"));
    
    treeNode.addChildNode(new TreeNode(1L,"商品管理","/menu.htm"));
    treeNode.addChildNode(1L,new TreeNode(101L,"商品管理",""));
    treeNode.addChildNode(101L,new TreeNode(1011L,"商品分类管理","/comm/sort_main.htm"));
    treeNode.addChildNode(101L,new TreeNode(1012L,"商品品牌管理","/comm/brand_main.htm"));
    treeNode.addChildNode(101L,new TreeNode(1013L,"商品基础信息管理","/comm/comm_main.htm"));
    treeNode.addChildNode(101L,new TreeNode(1014L,"待处理事项","/pending/pending_detail.htm"));
    
    treeNode.addChildNode(new TreeNode(3L,"会员管理","/menu.htm"));
    treeNode.addChildNode(3L,new TreeNode(31L,"会员管理",""));
    treeNode.addChildNode(31L,new TreeNode(311L,"会员信息","/member/member_main.htm"));
    treeNode.addChildNode(31L,new TreeNode(313L,"会员等级规则","/member/viprule/viprule_main.htm"));
    treeNode.addChildNode(31L,new TreeNode(312L,"会员消费记录","/member/consumeLog_main.htm"));
    treeNode.addChildNode(3L,new TreeNode(32L,"日志管理",""));
    treeNode.addChildNode(32L,new TreeNode(321L,"激活日志","/member/activelog/activelog_main.htm"));
    treeNode.addChildNode(32L,new TreeNode(322L,"等级变更日志","/member/viplog/viplog_main.htm"));
//    treeNode.addChildNode(3L,new TreeNode(33L,"站内信管理",""));
    treeNode.addChildNode(31L,new TreeNode(314L,"会员站内信","/content/stationmsg_main.htm?type=1"));
//    treeNode.addChildNode(33L,new TreeNode(332L,"发送日志","/member/msgsend_main.htm"));
    
    treeNode.addChildNode(new TreeNode(10L,"营销管理","/menu.htm"));
    treeNode.addChildNode(10L,new TreeNode(1001L,"营销管理",""));
    treeNode.addChildNode(1001L,new TreeNode(10011L,"营销信息","/activity/activity_list.htm"));
    treeNode.addChildNode(1001L,new TreeNode(10012L,"会员注册活动","/regactivity/regActivity_mainpage.htm"));
    treeNode.addChildNode(10L,new TreeNode(1002L,"积分管理",""));
    treeNode.addChildNode(1002L,new TreeNode(10021L,"积分规则","/activity/scorerule_main.htm"));
    treeNode.addChildNode(1002L,new TreeNode(10022L,"积分日志","/activity/scorelog_main.htm"));
    treeNode.addChildNode(10L,new TreeNode(1003L,"优惠券管理",""));
    treeNode.addChildNode(1003L,new TreeNode(10031L,"优惠券批次管理","/activity/cardbatch_main.htm"));
    treeNode.addChildNode(1003L,new TreeNode(10032L,"优惠券管理","/activity/card_main.htm"));
    treeNode.addChildNode(10L,new TreeNode(1004L,"广告管理",""));
    treeNode.addChildNode(1004L,new TreeNode(10041L,"广告信息","/content/ad_main.htm"));
    
    treeNode.addChildNode(new TreeNode(9L,"结算管理","/menu.htm"));
    treeNode.addChildNode(9L,new TreeNode(91L,"结算管理",""));
    treeNode.addChildNode(91L,new TreeNode(911L,"结算信息","/settlement/settlement_main.htm"));
    treeNode.addChildNode(9L,new TreeNode(92L,"供应商结算",""));
    treeNode.addChildNode(92L,new TreeNode(921L,"供应商结算","/settlement/supplier_settlement_main.htm?type=1"));
    treeNode.addChildNode(92L,new TreeNode(922L,"未付款业务结算单","/settlement/settlementorder_mainpage.htm?type=1"));
    treeNode.addChildNode(9L,new TreeNode(93L,"门店结算",""));
    treeNode.addChildNode(93L,new TreeNode(931L,"门店结算","/settlement/storesettlement_mainpage.htm?type=3"));
    treeNode.addChildNode(93L,new TreeNode(932L,"未付款业务结算单","/settlement/settlementorder_mainpage.htm?type=3"));
    treeNode.addChildNode(9L,new TreeNode(94L,"大客户结算",""));
    treeNode.addChildNode(94L,new TreeNode(941L,"往来业务单结算","/settlement/supplier_settlement_main.htm?type=2"));
    treeNode.addChildNode(94L,new TreeNode(942L,"未付款业务结算单","/settlement/settlementorder_mainpage.htm?type=2"));
    treeNode.addChildNode(9L,new TreeNode(95L,"物流费用结算",""));
    treeNode.addChildNode(95L,new TreeNode(951L,"物流费用结算","/deliveryorg/deliveryorg_main.htm?type=4"));
    treeNode.addChildNode(95L,new TreeNode(952L,"未付款结算单","/settlement/settlementorder_mainpage.htm?type=4"));
    
    treeNode.addChildNode(new TreeNode(12L,"系统管理","/menu.htm"));
    treeNode.addChildNode(12L,new TreeNode(1201L,"权限管理",""));
    treeNode.addChildNode(1201L,new TreeNode(12011L,"权限配置","/system/purview_main.htm"));
    treeNode.addChildNode(1201L,new TreeNode(12012L,"系统操作员管理","/system/op_main.htm?type=1"));
    treeNode.addChildNode(1201L,new TreeNode(12013L,"门店操作员管理","/system/op_main.htm?type=2"));
    treeNode.addChildNode(1201L,new TreeNode(12014L,"角色管理","/system/role_main.htm"));
    treeNode.addChildNode(1201L,new TreeNode(12015L,"日志管理","/system/log_main.htm"));
    treeNode.addChildNode(12L,new TreeNode(1202L,"审核管理",""));
//    treeNode.addChildNode(1202L,new TreeNode(12021L,"审核信息","/system/audits_main.htm"));
    treeNode.addChildNode(1202L,new TreeNode(12022L,"审核业务配置","/system/audconfig_main.htm"));
    treeNode.addChildNode(1202L,new TreeNode(12023L,"审核日志","/system/audexample_main.htm"));
    treeNode.addChildNode(12L,new TreeNode(1203L,"其他管理",""));
    treeNode.addChildNode(1203L,new TreeNode(12031L,"地区管理","/system/area_main.htm"));
    treeNode.addChildNode(1203L,new TreeNode(12032L,"配送机构管理","/system/delivery_main.htm"));
    treeNode.addChildNode(1203L,new TreeNode(12033L,"系统编码管理","/system/syscode_main.htm"));
    
    return treeNode;
  }

  /**
   * 获取默认二级菜单
   * @return
   */
  public PanelTreeMenuControl getDefaultMenu() {
    PanelTreeMenuControl getMenu = new PanelTreeMenuControl("panelTree",treeNode,this.defaultMenuID);
    return getMenu;
  }

  /**
   * 获取顶部菜单
   * @return
   */
  public TopMenuControl getTopMenu() {
    TopMenuControl topMenu = new TopMenuControl("topMenu",treeNode);
    return topMenu;
  }


  @Override
  public void onInit() {
    //获取id参数
    String tmpStr = getContext().getRequest().getParameter("_menuid");
    Long tmpV = -1L;
    try {
      tmpV = Long.parseLong(tmpStr);
    } catch (Exception e) {
    }
    if (tmpV < 0) tmpV = this.defaultMenuID;
    if (tmpV >= 0) {
      panelTree = new PanelTreeMenuControl("panelTree",treeNode,tmpV);
      this.addControl(panelTree);
    }
  }

  @Override
  public String getTemplate() {
      return "/clicktemplate/menu.htm";
  }
  
}
