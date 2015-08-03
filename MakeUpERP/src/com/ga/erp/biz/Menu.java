package com.ga.erp.biz;

import org.apache.click.Page;
import org.apache.click.util.Bindable;

import com.ga.click.control.tree.PanelTreeMenuControl;
import com.ga.click.control.tree.TopMenuControl;
import com.ga.click.control.tree.TreeNode;

/**
 * �˵�����,���ݵ�¼�û���Ȩ�����ù����˵�
 * ��ʽΪ�̶��Ķ����˵�������˵�,һ���˵�Ϊ��������,�������˵�Ϊ��������
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
   * ����ʱ��ʼ���˵��ṹ��������һ���˵���Ϊģʽ��ʾ�˵�
   */
  public Menu() {
	  
//    if (this.getContext().getRequest().getSession(false) != null) {
//      UserACL acl = (UserACL) this.getContext().getRequest().getSession(false).getAttribute("userAcl");
//      if (acl == null) {
//        throw new BizException("δ��¼");//δ��½
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
   * ��ʼ���˵�
   * @return
   */
  private TreeNode initTreeNode() {
    
    TreeNode treeNode = new TreeNode(0L,"���ڵ�","");
    
    treeNode.addChildNode(new TreeNode(11L,"ͳ�Ʒ�������","/menu.htm"));
    treeNode.addChildNode(11L,new TreeNode(1101L,"��Աͳ��",""));
//    treeNode.addChildNode(1101L,new TreeNode(11011L,"��Ա����","/supplier/supplier_main.htm"));
    treeNode.addChildNode(1101L,new TreeNode(11012L,"��Ա�ҳ϶ȷ���","/member/vipLoyaltyAn_main.htm"));
    treeNode.addChildNode(1101L,new TreeNode(11013L,"��Ա����Ⱥ�����","/statistic/member/MemGroup.jsp"));
    treeNode.addChildNode(11L,new TreeNode(1102L,"����ͳ��",""));
//    treeNode.addChildNode(1102L,new TreeNode(11021L,"���۷���","/supplier/supplier_main.htm"));
    treeNode.addChildNode(11L,new TreeNode(1103L,"�ŵ�ͳ��",""));
    treeNode.addChildNode(1103L,new TreeNode(11031L,"�������","/statistic/store/houseCnt.jsp"));
    treeNode.addChildNode(1103L,new TreeNode(11032L,"��������","/statistic/store/order.jsp"));
    treeNode.addChildNode(1103L,new TreeNode(11033L,"������Ʒ","/statistic/store/HotComm.jsp"));
    treeNode.addChildNode(1103L,new TreeNode(11034L,"����ABC","/abc/abc_mainpage.htm"));
    
    treeNode.addChildNode(new TreeNode(4L,"�ŵ����","/menu.htm"));
    treeNode.addChildNode(4L,new TreeNode(41L,"�ŵ����",""));
    treeNode.addChildNode(41L,new TreeNode(411L,"�ŵ���Ϣ","/store/store_main.htm"));
    treeNode.addChildNode(41L,new TreeNode(412L,"POS����Ϣ","/store/storepos/pos_main.htm"));
//    treeNode.addChildNode(41L,new TreeNode(413L,"�ŵ���õ�","/costorder/costorder_main.htm?type=3"));
    treeNode.addChildNode(4L,new TreeNode(42L,"�ŵ궩������",""));
    treeNode.addChildNode(42L,new TreeNode(421L,"���۶���","/store/storesalesorder/salesorder_main.htm"));
    treeNode.addChildNode(42L,new TreeNode(422L,"�˻�����", "/store/returnOrder_main.htm"));
//    treeNode.addChildNode(42L,new TreeNode(422L,"�ŵ����۶�����ϸ","/store/storesalesdetail/salesdetail_main.htm"));
    treeNode.addChildNode(4L,new TreeNode(43L,"�ŵ��쳣�澯",""));
    treeNode.addChildNode(43L,new TreeNode(431L,"�ŵ�ȱ���澯", "/store/storealert_main.htm?type=2"));
    treeNode.addChildNode(43L,new TreeNode(432L,"�ŵ긺�����Ʒ", "/store/storealert_main.htm?type=1"));
    treeNode.addChildNode(4L,new TreeNode(44L,"�ŵ���־����",""));
    treeNode.addChildNode(44L,new TreeNode(441L,"�ŵ�Ǯ����־","/store/storewalletlog/walletlog_main.htm"));
    treeNode.addChildNode(4L,new TreeNode(45L,"վ���Ź���",""));
    treeNode.addChildNode(45L,new TreeNode(451L,"�ŵ�վ����","/content/stationmsg_main.htm?type=2"));
    treeNode.addChildNode(4L,new TreeNode(46L,"�ŵ���Ʒ�۸����",""));
    treeNode.addChildNode(46L,new TreeNode(461L,"�۸������","/modifyprice/bill_main.htm?objType=3"));
//    treeNode.addChildNode(451L,new TreeNode(4511L,"������־","/member/msgsend_main.htm"));
    
    treeNode.addChildNode(new TreeNode(7L,"�ŵ굥�ݹ���","/menu.htm"));
    treeNode.addChildNode(7L,new TreeNode(71L,"�ŵ�Ҫ��",""));
    treeNode.addChildNode(71L,new TreeNode(712L,"�ŵ�Ҫ������", "/store/storeorder_main.htm?type=4"));
    treeNode.addChildNode(71L,new TreeNode(711L,"���ֱ������", "/store/storeorder_main.htm?type=1"));
    treeNode.addChildNode(7L,new TreeNode(72L,"�ŵ귢��",""));
    treeNode.addChildNode(72L,new TreeNode(721L,"�ŵ�ֱ������", "/store/storeorder_main.htm?type=4"));
    treeNode.addChildNode(7L,new TreeNode(73L,"�ŵ��ջ�",""));
    treeNode.addChildNode(73L,new TreeNode(731L,"���ֱ���ջ�", "/store/storeorder_main.htm?type=3"));
    treeNode.addChildNode(73L,new TreeNode(732L,"�ŵ�Ҫ���ջ�", "/store/storeorder_main.htm?type=6"));
    treeNode.addChildNode(73L,new TreeNode(733L,"���ֱ���˻��ջ�", "/store/storeorder_main.htm?type=10"));
    treeNode.addChildNode(7L,new TreeNode(74L,"�ŵ��˻�",""));
    treeNode.addChildNode(74L,new TreeNode(741L,"�ɹ��˻�����", "/store/storeorder_main.htm?type=7"));
    treeNode.addChildNode(74L,new TreeNode(742L,"ֱ���˻�����", "/store/storeorder_main.htm?type=9"));
//    treeNode.addChildNode(7L,new TreeNode(75L,"�ɹ���Ʒ",""));
//    treeNode.addChildNode(75L,new TreeNode(751L,"�ɹ�Ŀ¼��Ʒ","/store/storecataloguecomm_main.htm"));
    
    treeNode.addChildNode(new TreeNode(13L,"�̵����","/menu.htm"));
    treeNode.addChildNode(13L,new TreeNode(131L,"�ֿ��̵����",""));
    treeNode.addChildNode(131L,new TreeNode(1311L,"�̵����ι���","/stock/batch_main.htm?type=1"));
    treeNode.addChildNode(131L,new TreeNode(1312L,"�̵㵥����","/stock/bill_main.htm?type=1"));
    treeNode.addChildNode(131L,new TreeNode(1313L,"�̵���쵥","/stock/diffbill_main.htm?type=1"));
    
    treeNode.addChildNode(13L,new TreeNode(171L,"�ŵ��̵����",""));
    treeNode.addChildNode(171L,new TreeNode(1711L,"�̵����ι���","/stock/batch_main.htm?type=2"));
    treeNode.addChildNode(171L,new TreeNode(1712L,"�̵㵥����","/stock/bill_main.htm?type=2"));
    treeNode.addChildNode(171L,new TreeNode(1713L,"�̵���쵥","/stock/diffbill_main.htm?type=2"));
    
    treeNode.addChildNode(new TreeNode(8L,"�ֿ����","/menu.htm"));
    treeNode.addChildNode(8L,new TreeNode(81L,"�ֿ����",""));
    treeNode.addChildNode(81L,new TreeNode(811L,"�ֿ���Ϣ","/stock/stock_main.htm"));
    treeNode.addChildNode(81L,new TreeNode(812L,"������Ϣ","/stock/shelf_main.htm"));
    treeNode.addChildNode(81L,new TreeNode(813L,"�����Ʒ��Ϣ","/stock/comm_main.htm"));
    treeNode.addChildNode(8L,new TreeNode(82L,"�ֿ��쳣�澯",""));
    treeNode.addChildNode(82L,new TreeNode(821L,"���澯","/stock/stockalert_main.htm?type=2"));
    treeNode.addChildNode(82L,new TreeNode(822L,"�������Ʒ","/stock/stockalert_main.htm?type=1"));
    treeNode.addChildNode(8L,new TreeNode(83L,"�ֿ���Ʒ�۸����",""));
    treeNode.addChildNode(83L,new TreeNode(831L,"�ֿ�۸������","/modifyprice/bill_main.htm?objType=2"));
    
    treeNode.addChildNode(new TreeNode(14L,"DC���ݹ���","/menu.htm"));
    treeNode.addChildNode(14L,new TreeNode(141L,"DCҪ��",""));
    treeNode.addChildNode(141L,new TreeNode(1411L,"�ɹ����뵥","/purchase/purOrder_main.htm?orderType=1"));
    
    treeNode.addChildNode(14L,new TreeNode(143L,"DC����",""));
    treeNode.addChildNode(143L,new TreeNode(1421L,"�ŵ�Ҫ����","/store/storeorder_main.htm?type=2"));
    treeNode.addChildNode(143L,new TreeNode(1431L,"��ͻ�Ҫ����","/purchase/purOrder_main.htm?orderType=6"));
    treeNode.addChildNode(143L,new TreeNode(1432L,"��ͻ����۵�","/purchase/purOrder_main.htm?orderType=7"));
    
    treeNode.addChildNode(14L,new TreeNode(142L,"DC�ջ�",""));
    treeNode.addChildNode(142L,new TreeNode(1412L,"�ɹ��ջ���","/purchase/purOrder_main.htm?orderType=3"));
    treeNode.addChildNode(142L,new TreeNode(1422L,"�ŵ��˻���","/store/storeorder_main.htm?type=3"));
    treeNode.addChildNode(142L,new TreeNode(1433L,"��ͻ��˻���","/purchase/purOrder_main.htm?orderType=8"));
    
//    treeNode.addChildNode(141L,new TreeNode (1411L,"�ɹ��嵥","/purchase/purList_main.htm"));
    treeNode.addChildNode(14L,new TreeNode(144L,"DC�˻�",""));
    treeNode.addChildNode(144L,new TreeNode(1413L,"�ɹ��˻���","/purchase/purOrder_main.htm?orderType=5"));

    
    treeNode.addChildNode(new TreeNode(16L,"��Ӧ�̵��ݹ���","/menu.htm"));
    treeNode.addChildNode(16L,new TreeNode(161L,"��Ӧ�̷���",""));
    treeNode.addChildNode(161L,new TreeNode(1611L,"DCҪ����","/purchase/purOrder_main.htm?orderType=1"));
    
    treeNode.addChildNode(new TreeNode(6L,"��Ӧ�̹���","/menu.htm"));
    treeNode.addChildNode(6L,new TreeNode(61L,"��Ӧ�̹���",""));
    treeNode.addChildNode(61L,new TreeNode(611L,"������Ϣά��","/supplier/supplier_main.htm?suppliertype=1"));
//    treeNode.addChildNode(61L,new TreeNode(612L,"��Ӧ�̷��õ�","/costorder/costorder_main.htm?type=1&&suppliertype=1"));
//    treeNode.addChildNode(61L,new TreeNode(612L,"��Ӧ�̷ϳ�","/supplier/supplier_main.htm"));
//    treeNode.addChildNode(61L,new TreeNode(613L,"��Ӧ�̹���","/supplier/supplier_main.htm"));
//    treeNode.addChildNode(6L,new TreeNode(62L,"��������",""));
//    treeNode.addChildNode(62L,new TreeNode(621L,"��Ӧ��Ʒ��Ϣ","/supplier/commodity/suppliercommodity_main.htm?suppliertype=1"));
//    treeNode.addChildNode(6L,new TreeNode(63L,"��ͬ����",""));
//    treeNode.addChildNode(63L,new TreeNode(631L,"��ͬ��Ϣ","/supplier/contract_main.htm?suppliertype=1"));
//    treeNode.addChildNode(6L,new TreeNode(64L,"վ���Ź���",""));
    treeNode.addChildNode(61L,new TreeNode(612L,"��Ӧ��վ����","/content/stationmsg_main.htm?type=3&&suppliertype=1"));
//    treeNode.addChildNode(64L,new TreeNode(642L,"������־","/member/msgsend_main.htm"));
    
    treeNode.addChildNode(new TreeNode(15L,"��ͻ�����","/menu.htm"));
    treeNode.addChildNode(15L,new TreeNode(151L,"��ͻ�����",""));
    treeNode.addChildNode(151L,new TreeNode(1511L,"������Ϣά��","/supplier/supplier_main.htm?suppliertype=2"));
//    treeNode.addChildNode(151L,new TreeNode(1512L,"��ͻ����õ�","/costorder/costorder_main.htm?type=1&&suppliertype=2"));
//    treeNode.addChildNode(15L,new TreeNode(153L,"��ͬ����",""));
//    treeNode.addChildNode(153L,new TreeNode(1531L,"��ͬ��Ϣ","/supplier/contract_main.htm?suppliertype=2"));
//    treeNode.addChildNode(15L,new TreeNode(154L,"վ���Ź���",""));
    treeNode.addChildNode(151L,new TreeNode(1512L,"��ͻ�վ����","/content/stationmsg_main.htm?type=3&&suppliertype=2"));
//    treeNode.addChildNode(64L,new TreeNode(642L,"������־","/member/msgsend_main.htm"));
//    treeNode.addChildNode(6L,new TreeNode(65L,"��Ӧ����Ʒ�۸����",""));
    treeNode.addChildNode(61L,new TreeNode(613L,"��Ʒ��������","/modifyprice/bill_main.htm?objType=1"));
    
    treeNode.addChildNode(new TreeNode(1L,"��Ʒ����","/menu.htm"));
    treeNode.addChildNode(1L,new TreeNode(101L,"��Ʒ����",""));
    treeNode.addChildNode(101L,new TreeNode(1011L,"��Ʒ�������","/comm/sort_main.htm"));
    treeNode.addChildNode(101L,new TreeNode(1012L,"��ƷƷ�ƹ���","/comm/brand_main.htm"));
    treeNode.addChildNode(101L,new TreeNode(1013L,"��Ʒ������Ϣ����","/comm/comm_main.htm"));
    treeNode.addChildNode(101L,new TreeNode(1014L,"����������","/pending/pending_detail.htm"));
    
    treeNode.addChildNode(new TreeNode(3L,"��Ա����","/menu.htm"));
    treeNode.addChildNode(3L,new TreeNode(31L,"��Ա����",""));
    treeNode.addChildNode(31L,new TreeNode(311L,"��Ա��Ϣ","/member/member_main.htm"));
    treeNode.addChildNode(31L,new TreeNode(313L,"��Ա�ȼ�����","/member/viprule/viprule_main.htm"));
    treeNode.addChildNode(31L,new TreeNode(312L,"��Ա���Ѽ�¼","/member/consumeLog_main.htm"));
    treeNode.addChildNode(3L,new TreeNode(32L,"��־����",""));
    treeNode.addChildNode(32L,new TreeNode(321L,"������־","/member/activelog/activelog_main.htm"));
    treeNode.addChildNode(32L,new TreeNode(322L,"�ȼ������־","/member/viplog/viplog_main.htm"));
//    treeNode.addChildNode(3L,new TreeNode(33L,"վ���Ź���",""));
    treeNode.addChildNode(31L,new TreeNode(314L,"��Ավ����","/content/stationmsg_main.htm?type=1"));
//    treeNode.addChildNode(33L,new TreeNode(332L,"������־","/member/msgsend_main.htm"));
    
    treeNode.addChildNode(new TreeNode(10L,"Ӫ������","/menu.htm"));
    treeNode.addChildNode(10L,new TreeNode(1001L,"Ӫ������",""));
    treeNode.addChildNode(1001L,new TreeNode(10011L,"Ӫ����Ϣ","/activity/activity_list.htm"));
    treeNode.addChildNode(1001L,new TreeNode(10012L,"��Աע��","/regactivity/regActivity_mainpage.htm"));
    treeNode.addChildNode(10L,new TreeNode(1002L,"���ֹ���",""));
    treeNode.addChildNode(1002L,new TreeNode(10021L,"���ֹ���","/activity/scorerule_main.htm"));
    treeNode.addChildNode(1002L,new TreeNode(10022L,"������־","/activity/scorelog_main.htm"));
    treeNode.addChildNode(10L,new TreeNode(1003L,"�Ż�ȯ����",""));
    treeNode.addChildNode(1003L,new TreeNode(10031L,"�Ż�ȯ���ι���","/activity/cardbatch_main.htm"));
    treeNode.addChildNode(1003L,new TreeNode(10032L,"�Ż�ȯ����","/activity/card_main.htm"));
    treeNode.addChildNode(10L,new TreeNode(1004L,"������",""));
    treeNode.addChildNode(1004L,new TreeNode(10041L,"�����Ϣ","/content/ad_main.htm"));
    
    treeNode.addChildNode(new TreeNode(9L,"�������","/menu.htm"));
    treeNode.addChildNode(9L,new TreeNode(91L,"�������",""));
    treeNode.addChildNode(91L,new TreeNode(911L,"������Ϣ","/settlement/settlement_main.htm"));
    treeNode.addChildNode(9L,new TreeNode(92L,"��Ӧ�̽���",""));
    treeNode.addChildNode(92L,new TreeNode(921L,"��Ӧ�̽���","/settlement/supplier_settlement_main.htm?type=1"));
    treeNode.addChildNode(92L,new TreeNode(922L,"δ����ҵ����㵥","/settlement/settlementorder_mainpage.htm?type=1"));
    treeNode.addChildNode(9L,new TreeNode(93L,"�ŵ����",""));
    treeNode.addChildNode(93L,new TreeNode(931L,"�ŵ����","/settlement/storesettlement_mainpage.htm?type=3"));
    treeNode.addChildNode(93L,new TreeNode(932L,"δ����ҵ����㵥","/settlement/settlementorder_mainpage.htm?type=3"));
    treeNode.addChildNode(9L,new TreeNode(94L,"��ͻ�����",""));
    treeNode.addChildNode(94L,new TreeNode(941L,"����ҵ�񵥽���","/settlement/supplier_settlement_main.htm?type=2"));
    treeNode.addChildNode(94L,new TreeNode(942L,"δ����ҵ����㵥","/settlement/settlementorder_mainpage.htm?type=2"));
    treeNode.addChildNode(9L,new TreeNode(95L,"�������ý���",""));
    treeNode.addChildNode(95L,new TreeNode(951L,"�������ý���","/deliveryorg/deliveryorg_main.htm?type=4"));
    treeNode.addChildNode(95L,new TreeNode(952L,"δ������㵥","/settlement/settlementorder_mainpage.htm?type=4"));
    
    treeNode.addChildNode(new TreeNode(12L,"ϵͳ����","/menu.htm"));
    treeNode.addChildNode(12L,new TreeNode(1201L,"Ȩ�޹���",""));
    treeNode.addChildNode(1201L,new TreeNode(12011L,"Ȩ������","/system/purview_main.htm"));
    treeNode.addChildNode(1201L,new TreeNode(12012L,"ϵͳ����Ա����","/system/op_main.htm?type=1"));
    treeNode.addChildNode(1201L,new TreeNode(12013L,"�ŵ����Ա����","/system/op_main.htm?type=2"));
    treeNode.addChildNode(1201L,new TreeNode(12014L,"��ɫ����","/system/role_main.htm"));
    treeNode.addChildNode(1201L,new TreeNode(12015L,"��־����","/system/log_main.htm"));
    treeNode.addChildNode(12L,new TreeNode(1202L,"��˹���",""));
//    treeNode.addChildNode(1202L,new TreeNode(12021L,"�����Ϣ","/system/audits_main.htm"));
    treeNode.addChildNode(1202L,new TreeNode(12022L,"���ҵ������","/system/audconfig_main.htm"));
    treeNode.addChildNode(1202L,new TreeNode(12023L,"�����־","/system/audexample_main.htm"));
    treeNode.addChildNode(12L,new TreeNode(1203L,"��������",""));
    treeNode.addChildNode(1203L,new TreeNode(12031L,"��������","/system/area_main.htm"));
    treeNode.addChildNode(1203L,new TreeNode(12032L,"���ͻ�������","/system/delivery_main.htm"));
    treeNode.addChildNode(1203L,new TreeNode(12033L,"ϵͳ�������","/system/syscode_main.htm"));
    
    return treeNode;
  }

  /**
   * ��ȡĬ�϶����˵�
   * @return
   */
  public PanelTreeMenuControl getDefaultMenu() {
    PanelTreeMenuControl getMenu = new PanelTreeMenuControl("panelTree",treeNode,this.defaultMenuID);
    return getMenu;
  }

  /**
   * ��ȡ�����˵�
   * @return
   */
  public TopMenuControl getTopMenu() {
    TopMenuControl topMenu = new TopMenuControl("topMenu",treeNode);
    return topMenu;
  }


  @Override
  public void onInit() {
    //��ȡid����
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