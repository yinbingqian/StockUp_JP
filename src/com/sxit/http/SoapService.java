package com.sxit.http;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.sxit.activity.adviser.th.item.bean.Question_ListBean;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.th.item.bean.AskQuestion_ListBean;
import com.sxit.entity.AnalystVideo;
import com.sxit.entity.LoginUser;
import com.sxit.entity.PublishMessage;
import com.sxit.entity.PublishMessageInfo;
import com.sxit.entity.anwser.Anwser;
import com.sxit.entity.anwser.AnwserInfo;
import com.sxit.entity.news.FinNews;
import com.sxit.entity.news.FinNewsInfo;
import com.sxit.entity.news.FinNewsList;
import com.sxit.http.AsyncTaskBase.SoapObjectResult;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;

import android.content.Intent;

public class SoapService implements ISoapService {
	private AsyncTaskBase asynTaskBase = new AsyncTaskBase();
	private SoapRes soapRes = new SoapRes();

	@Override
	public void adminLogin(Object[] property_va) {
		String[] property_nm = { "name", "password" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.ADMINLOGIN);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				LoginUser loginUser = null;
				SoapObject soapchild = (SoapObject) obj.getProperty(0);
				if (soapchild.getProperty("Id").toString().equals("0")) {

				} else {
					loginUser = new LoginUser();
					loginUser.setUserid(soapchild.getProperty("Id").toString());
					loginUser.setGroupid(soapchild.getProperty("GroupId")
							.toString());
					loginUser.setName(soapchild.getProperty("Name").toString());
					loginUser.setPassword(soapchild.getProperty("Password")
							.toString());
					loginUser.setRealname(soapchild.getProperty("RealName")
							.toString());
					loginUser.setMobilephone(soapchild.getProperty(
							"Mobilephone").toString());
					loginUser.setEmail(soapchild.getProperty("Email")
							.toString());
					loginUser.setCrtime(soapchild.getProperty("CrTime")
							.toString());
					loginUser.setIslock(soapchild.getProperty("Islock")
							.toString());
					loginUser.setSex(soapchild.getProperty("Sex").toString());
					loginUser.setOrgid(soapchild.getProperty("Orgid")
							.toString());
					loginUser.setSpecialty(soapchild.getProperty("Specialty")
							.toString());
					loginUser.setResume(soapchild.getProperty("Resume")
							.toString());
					loginUser.setLevel(soapchild.getProperty("Level")
							.toString());
					loginUser.setStatus(soapchild.getProperty("Status")
							.toString());
					loginUser.setMark(soapchild.getProperty("Mark").toString());
					loginUser.setRewardmark(soapchild.getProperty("Rewardmark")
							.toString());
					loginUser.setPaidmark(soapchild.getProperty("Paidmark")
							.toString());
					loginUser.setHeadpic(soapchild.getProperty("HeadPic")
							.toString());
					loginUser.setPtitle(soapchild.getProperty("PTitle")
							.toString());
					loginUser.setOrgname(soapchild.getProperty("OrgName")
							.toString());
					MyApplication.getInstance().setA_heartcount(
							soapchild.getProperty("Heartcount").toString());
				}
				soapRes.setObj(loginUser);
				soapRes.setCode(SOAP_UTILS.METHOD.ADMINLOGIN);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.ADMINLOGIN);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void communReplyAdd(Object[] property_va) {
		String[] property_nm = { "replyid", "replyAbstract", "content",
				"userId" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.COMMUNREPLYADD);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				System.out.println("-----communReplyAdd = " + obj);
				soapRes.setObj(obj.getProperty("CommunReplyAddResult"));
				soapRes.setCode(SOAP_UTILS.METHOD.COMMUNREPLYADD);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.COMMUNREPLYADD);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void communicationAdd(Object[] property_va) {
		String[] property_nm = { "title", "content", "userId", "mark", "aging",
				"images" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.COMMUNICATIONADD);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("CommunicationAddResult"));
				soapRes.setCode(SOAP_UTILS.METHOD.COMMUNICATIONADD);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.COMMUNICATIONADD);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getAdmin(Object[] property_va) {
		String[] property_nm = { "pagesize", "pageindex" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETADMIN);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj);
				soapRes.setCode(SOAP_UTILS.METHOD.GETADMIN);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {

			}
		});
	}

	@Override
	public void getColumns(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "pagesize", "pageindex" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETCOLUMNS);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				System.out.println("-----getColumns =" + obj);
				SoapObject soapchild = (SoapObject) obj.getProperty(0);
				SoapObject soapchids = (SoapObject) soapchild.getProperty(1);
				List<FinNews> list = new ArrayList<FinNews>();
				if (soapchids.getPropertyCount() != 0) {
					SoapObject soapchidss = (SoapObject) soapchids
							.getProperty(0);
					int item_count = soapchidss.getPropertyCount();
					for (int i = 0; i < item_count; i++) {
						SoapObject soapchildsson = (SoapObject) soapchidss
								.getProperty(i);
						FinNews bean = new FinNews();
						bean.setId(soapchildsson.getProperty("id").toString());
						bean.setValue(soapchildsson.getProperty("value")
								.toString());
						bean.setTitle(soapchildsson.getProperty("title")
								.toString());
						bean.setTemplate(SOAP_UTILS.COLUMN_PIC_PATH
								+ soapchildsson.getProperty("template")
										.toString());
						bean.setNewsTitle(soapchildsson
								.getProperty("newsTitle").toString());
						bean.setCrtime(soapchildsson.getProperty("crtime")
								.toString());
						list.add(bean);
					}
				}
				soapRes.setObj(list);
				soapRes.setPage(isPage);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCOLUMNS);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setPage(isPage);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCOLUMNS);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getCommunReply(Object[] property_va, final List<Anwser> list) {
		String[] property_nm = { "id" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETCOMMUNREPLY);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				SoapObject soapchild = (SoapObject) obj.getProperty(0);
				System.out.println("------getCommunReply =" + obj);
				if (soapchild.getPropertyCount() != 0) {
					int item_count = soapchild.getPropertyCount();
					for (int i = 0; i < item_count; i++) {
						SoapObject soapchildsson = (SoapObject) soapchild
								.getProperty(i);
						Anwser anwser = new Anwser();
						anwser.setTitle(soapchildsson.getProperty(
								"ReplyAbstract").toString());
						anwser.setContent_img(null);
						anwser.setMsg_direction(1);
						anwser.setDate(soapchildsson.getProperty("Crtime")
								.toString());
						anwser.setContent(soapchildsson.getProperty("Content")
								.toString());
						anwser.setUser_img(SOAP_UTILS.HEADER_URL
								+ soapchildsson.getProperty("HeadPic")
										.toString());
						anwser.setContent_img(SOAP_UTILS.HEADER_URL
								+ soapchildsson.getProperty("HeadPic")
										.toString());
						anwser.setReplyId(soapchildsson.getProperty("Replyid")
								.toString());// 问题id
						anwser.setAnwserId(soapchildsson.getProperty("Id")
								.toString());// 回复id(用户查询解答详情)
						anwser.setAnalystId(soapchildsson.getProperty("Userid")
								.toString());
						anwser.setRealName(soapchildsson
								.getProperty("RealName").toString());
						anwser.setBestAnswer(soapchildsson.getProperty(
								"Bestanswer").toString());
						anwser.setNswerMark(soapchildsson.getProperty(
								"Answermark").toString());
						list.add(anwser);
					}
				}
				soapRes.setObj(list);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCOMMUNREPLY);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {

			}
		});
	}

	@Override
	public void getCommunReplyAna(Object[] property_va) {
		String[] property_nm = { "id", "AnalystID" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETCOMMUNREPLYANA);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCOMMUNREPLYANA);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {

			}
		});
	}

	@Override
	public void getCommunReplyByID(Object[] property_va) {
		String[] property_nm = { "id" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETCOMMUNREPLYBYID);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				System.out.println("-------getCommunReplyByID =" + obj);
				SoapObject soap = (SoapObject) obj.getProperty(0);
				SoapObject soapchild = (SoapObject) soap.getProperty(0);
				AnwserInfo info = null;
				if (soapchild.getProperty("Id").toString().equals("0")) {

				} else {
					info = new AnwserInfo();
					info.setId(soapchild.getProperty("Id").toString());
					info.setReplyid(soapchild.getProperty("Replyid").toString());
					info.setUserid(soapchild.getProperty("Userid").toString());
					info.setType(soapchild.getProperty("Type").toString());
					info.setCrtime(soapchild.getProperty("Crtime").toString());
					info.setBestanswer(soapchild.getProperty("Bestanswer")
							.toString());
					info.setAnswermark(soapchild.getProperty("Answermark")
							.toString());
					info.setReplyAbstract(soapchild
							.getProperty("ReplyAbstract").toString());
//					String content = soapchild.getProperty("Content").toString();
					info.setContent(soapchild.getProperty("Content").toString());
					info.setRealName(soapchild.getProperty("RealName")
							.toString());
					info.setGroupId(soapchild.getProperty("GroupId").toString());
					info.setMobilephone(soapchild.getProperty("Mobilephone")
							.toString());
					info.setEmail(soapchild.getProperty("Email").toString());
					info.setIslock(soapchild.getProperty("Islock").toString());
					info.setSex(soapchild.getProperty("Sex").toString());
					info.setOrgid(soapchild.getProperty("Orgid").toString());
					info.setSpecialty(soapchild.getProperty("Specialty")
							.toString());
					info.setResume(soapchild.getProperty("Resume").toString());
					info.setLevel(soapchild.getProperty("Level").toString());
					info.setStatus(soapchild.getProperty("Status").toString());
					info.setMark(soapchild.getProperty("Mark").toString());
					info.setRewardmark(soapchild.getProperty("Rewardmark")
							.toString());
					info.setPaidmark(soapchild.getProperty("Paidmark")
							.toString());
					info.setHeadPic(SOAP_UTILS.HEADER_URL
							+ soapchild.getProperty("HeadPic").toString());
					info.setpTitle(soapchild.getProperty("PTitle").toString());
					info.setOrgName(soapchild.getProperty("OrgName").toString());
					info.setCommStatus(soapchild.getProperty("CommStatus")
							.toString());
				}
				soapRes.setObj(info);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCOMMUNREPLYBYID);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCOMMUNREPLYBYID);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}
	@Override
	public void getBestAnswer(Object[] property_va) {
		String[] property_nm = { "id" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETBESTANSWER);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {
			
			@Override
			public void soapResult(SoapObject obj) {
				System.out.println("-------getBestAnswer =" + obj);
				SoapObject soap = (SoapObject) obj.getProperty(0);
				if(soap.toString().startsWith("anyType")){
					soapRes.setObj("");
					soapRes.setCode(SOAP_UTILS.METHOD.GETBESTANSWER);
					EventCache.commandActivity.post(soapRes);
				}else{
					
					SoapObject soapchild = (SoapObject) soap.getProperty(0);
					
					AnwserInfo info = null;
					if (soapchild.getProperty("Id").toString().equals("0")) {
						
					} else {
						info = new AnwserInfo();
						info.setId(soapchild.getProperty("Id").toString());
						info.setReplyid(soapchild.getProperty("Replyid").toString());
						info.setUserid(soapchild.getProperty("Userid").toString());
						info.setType(soapchild.getProperty("Type").toString());
						info.setCrtime(soapchild.getProperty("Crtime").toString());
						info.setBestanswer(soapchild.getProperty("Bestanswer")
								.toString());
						info.setAnswermark(soapchild.getProperty("Answermark")
								.toString());
						info.setReplyAbstract(soapchild
								.getProperty("ReplyAbstract").toString());
//					String content = soapchild.getProperty("Content").toString();
						info.setContent(soapchild.getProperty("Content").toString());
						info.setRealName(soapchild.getProperty("RealName")
								.toString());
						info.setGroupId(soapchild.getProperty("GroupId").toString());
						info.setMobilephone(soapchild.getProperty("Mobilephone")
								.toString());
						info.setEmail(soapchild.getProperty("Email").toString());
						info.setIslock(soapchild.getProperty("Islock").toString());
						info.setSex(soapchild.getProperty("Sex").toString());
						info.setOrgid(soapchild.getProperty("Orgid").toString());
						info.setSpecialty(soapchild.getProperty("Specialty")
								.toString());
						info.setResume(soapchild.getProperty("Resume").toString());
						info.setLevel(soapchild.getProperty("Level").toString());
						info.setStatus(soapchild.getProperty("Status").toString());
						info.setMark(soapchild.getProperty("Mark").toString());
						info.setRewardmark(soapchild.getProperty("Rewardmark")
								.toString());
						info.setPaidmark(soapchild.getProperty("Paidmark")
								.toString());
						info.setHeadPic(SOAP_UTILS.HEADER_URL
								+ soapchild.getProperty("HeadPic").toString());
						info.setpTitle(soapchild.getProperty("PTitle").toString());
						info.setOrgName(soapchild.getProperty("OrgName").toString());
						info.setCommStatus(soapchild.getProperty("CommStatus")
								.toString());
					}
					soapRes.setObj(info);
					soapRes.setCode(SOAP_UTILS.METHOD.GETBESTANSWER);
					EventCache.commandActivity.post(soapRes);
				}
			}
			
			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETBESTANSWER);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getCommunication(Object[] property_va) {
		String[] property_nm = { "pagesize", "pageindex", "userid", "status" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETCOMMUNICATION);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCOMMUNICATION);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {

			}
		});
	}

	@Override
	public void getCommunicationALL(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "pagesize", "pageindex", "userid" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETCOMMUNICATIONALL);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				System.out.println("------getCommunicationALL =" + obj);
				SoapObject soapchild = (SoapObject) obj.getProperty(0);
				SoapObject soapchids = (SoapObject) soapchild.getProperty(1);
				List<AskQuestion_ListBean> list = new ArrayList<AskQuestion_ListBean>();
				if (soapchids.getPropertyCount() != 0) {
					SoapObject soapchidss = (SoapObject) soapchids
							.getProperty(0);
					int item_count = soapchidss.getPropertyCount();
					for (int i = 0; i < item_count; i++) {
						SoapObject soapchildsson = (SoapObject) soapchidss
								.getProperty(i);
						AskQuestion_ListBean bean = new AskQuestion_ListBean();
						bean.setId(soapchildsson.getProperty("id").toString());
						bean.setTitle(soapchildsson.getProperty("title")
								.toString());
						bean.setContent(soapchildsson.getProperty("content")
								.toString());
						bean.setCrtime(soapchildsson.getProperty("crtime")
								.toString());
						bean.setMark(soapchildsson.getProperty("mark")
								.toString());
						bean.setAging(soapchildsson.getProperty("aging")
								.toString());
						bean.setStatus(soapchildsson.getProperty("status")
								.toString());
						bean.setCount(soapchildsson.getProperty("count")
								.toString());
						bean.setImgUrl(SOAP_UTILS.HEADER_URL
								+ soapchildsson.getProperty("imgUrl")
										.toString());
						bean.setBestAnswer(soapchildsson.getProperty(
								"bestAnswer").toString());
						list.add(bean);
					}
				}
				soapRes.setObj(list);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCOMMUNICATIONALL);
				soapRes.setPage(isPage);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCOMMUNICATIONALL);
				soapRes.setPage(isPage);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getCommunicationAllUser(Object[] property_va,
			final boolean isPage) {
		String[] property_nm = { "pagesize", "pageindex", "analystid" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETCOMMUNICATIONALLUSER);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				SoapObject soapchild = (SoapObject) obj.getProperty(0);
				SoapObject soapchids = (SoapObject) soapchild.getProperty(1);
				List<Question_ListBean> list = new ArrayList<Question_ListBean>();
				if (soapchids.getPropertyCount() != 0) {
					SoapObject soapchidss = (SoapObject) soapchids
							.getProperty(0);
					int item_count = soapchidss.getPropertyCount();
					for (int i = 0; i < item_count; i++) {
						SoapObject soapchildsson = (SoapObject) soapchidss
								.getProperty(i);
						Question_ListBean bean = new Question_ListBean();
						bean.setId(soapchildsson.getProperty("id").toString());
						bean.setTitle(soapchildsson.getProperty("title")
								.toString());
						bean.setContent(soapchildsson.getProperty("content")
								.toString());
						bean.setCrtime(soapchildsson.getProperty("crtime")
								.toString());
						String mark = soapchildsson.getProperty("mark")
								.toString();
						bean.setMark(soapchildsson.getProperty("mark")
								.toString());
						bean.setAging(soapchildsson.getProperty("aging")
								.toString());
						bean.setStatus(soapchildsson.getProperty("status")
								.toString());
						bean.setCount(soapchildsson.getProperty("count")
								.toString());
						bean.setUserName(soapchildsson.getProperty("userName")
								.toString());
						bean.setHeadpic(SOAP_UTILS.HEADER_URL
								+ soapchildsson.getProperty("headpic")
										.toString());
						list.add(bean);

						Intent intent = new Intent();
						intent.putExtra("mark", mark);
					}
				}
				soapRes.setObj(list);
				soapRes.setPage(isPage);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCOMMUNICATIONALLUSER);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setPage(isPage);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCOMMUNICATIONALLUSER);
				EventCache.commandActivity.post(soapRes);
			}
		});
		}

	@Override
	public void getCommunicationAna(Object[] property_va) {
		String[] property_nm = { "pagesize", "pageindex", "adminid", "flag" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETCOMMUNICATIONANA);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCOMMUNICATIONANA);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {

			}
		});
	}

	@Override
	public void getNewsContent(Object[] property_va) {
		String[] property_nm = { "id" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETNEWSCONTENT);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				SoapObject soapchildsson = (SoapObject) obj.getProperty(0);
				FinNewsInfo info = new FinNewsInfo();
				info.setIsRecommend(soapchildsson.getProperty("IsRecommend")
						.toString());
				info.setId(soapchildsson.getProperty("Id").toString());
				info.setCol(soapchildsson.getProperty("Col").toString());
				info.setOrders(soapchildsson.getProperty("Orders").toString());
				info.setTitle(soapchildsson.getProperty("Title").toString());
				info.setThumbnail(SOAP_UTILS.PIC_YANBAO
						+ soapchildsson.getProperty("Thumbnail").toString());
				info.setSource(soapchildsson.getProperty("Source").toString());
				info.setAuthor(soapchildsson.getProperty("Author").toString());
				info.setPicture(SOAP_UTILS.PIC_YANBAO
						+ soapchildsson.getProperty("Picture").toString());
				info.setContent(soapchildsson.getProperty("Content").toString());
				info.setAdminid(soapchildsson.getProperty("Adminid").toString());
				info.setCrtime(soapchildsson.getProperty("Crtime").toString());
				info.setImportant(soapchildsson.getProperty("Important")
						.toString());
				soapRes.setObj(info);
				soapRes.setCode(SOAP_UTILS.METHOD.GETNEWSCONTENT);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETNEWSCONTENT);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getNewsTitle(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "colid", "pagesize", "pageindex" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETNEWSTITLE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				SoapObject soapchids = (SoapObject) obj.getProperty(0);
				List<FinNewsList> list = new ArrayList<FinNewsList>();
				if (soapchids.getPropertyCount() != 0) {
					int item_count = soapchids.getPropertyCount();
					for (int i = 0; i < item_count; i++) {
						SoapObject soapchildsson = (SoapObject) soapchids
								.getProperty(i);
						FinNewsList bean = new FinNewsList();
						bean.setId(soapchildsson.getProperty("Id").toString());
						bean.setCommentCount(soapchildsson.getProperty(
								"CommentCount").toString());
						bean.setIsRecommend(soapchildsson.getProperty(
								"IsRecommend").toString());
						bean.setCol(soapchildsson.getProperty("Col").toString());
						bean.setOrders(soapchildsson.getProperty("Orders")
								.toString());
						bean.setTitle(soapchildsson.getProperty("Title")
								.toString());
						bean.setThumbnail(SOAP_UTILS.PIC_YANBAO
								+ soapchildsson.getProperty("Thumbnail")
										.toString());
						bean.setSource(soapchildsson.getProperty("Source")
								.toString());
						bean.setAuthor(soapchildsson.getProperty("Author")
								.toString());
						bean.setPicture(SOAP_UTILS.PIC_YANBAO
								+ soapchildsson.getProperty("Picture")
										.toString());
						bean.setContent(soapchildsson.getProperty("Content")
								.toString());
						bean.setAdminid(soapchildsson.getProperty("Adminid")
								.toString());
						bean.setCrtime(soapchildsson.getProperty("Crtime")
								.toString());
						bean.setImportant(soapchildsson
								.getProperty("Important").toString());
						bean.setValue(soapchildsson.getProperty("Value")
								.toString());
						bean.setColTitle(soapchildsson.getProperty("ColTitle")
								.toString());
						list.add(bean);
					}
				}
				soapRes.setObj(list);
				soapRes.setPage(isPage);
				soapRes.setCode(SOAP_UTILS.METHOD.GETNEWSTITLE);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setPage(isPage);
				soapRes.setCode(SOAP_UTILS.METHOD.GETNEWSTITLE);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void interactionHomepage(Object[] property_va) {
		String[] property_nm = { "toUser" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.INTERACTIONHOMEPAGE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj);
				soapRes.setCode(SOAP_UTILS.METHOD.INTERACTIONHOMEPAGE);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {

			}
		});
	}

	@Override
	public void interactionMessage(Object[] property_va) {
		String[] property_nm = { "toUser", "historyTime" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.INTERACTIONMESSAGE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj);
				soapRes.setCode(SOAP_UTILS.METHOD.INTERACTIONMESSAGE);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {

			}
		});
	}

	@Override
	public void interactionMessageAna(Object[] property_va) {
		String[] property_nm = { "fromUser", "historyTime" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.INTERACTIONMESSAGEANA);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj);
				soapRes.setCode(SOAP_UTILS.METHOD.INTERACTIONMESSAGEANA);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {

			}
		});
	}

	@Override
	public void interactionMessageByID(Object[] property_va) {
		String[] property_nm = { "ID" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.INTERACTIONMESSAGEBYID);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj);
				soapRes.setCode(SOAP_UTILS.METHOD.INTERACTIONMESSAGEBYID);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {

			}
		});
	}

	@Override
	public void interactionSubmit(Object[] property_va) {
		String[] property_nm = { "fromUser", "toUser", "blogTitle",
				"blogAbstract", "content", "devType", "infoType",
				"infoDirection" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.INTERACTIONSUBMIT);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj);
				soapRes.setCode(SOAP_UTILS.METHOD.INTERACTIONSUBMIT);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {

			}
		});
	}

	@Override
	public void updataBestAnswer(Object[] property_va) {
		String[] property_nm = { "id" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.UPDATABESTANSWER);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("UpdataBestAnswerResult"));
				soapRes.setCode(SOAP_UTILS.METHOD.UPDATABESTANSWER);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.UPDATABESTANSWER);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userInfoLogin(Object[] property_va) {
		String[] property_nm = { "name", "password", "imei" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USERINFOLOGIN);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				LoginUser loginUser = null;
				SoapObject soapchild = (SoapObject) obj.getProperty(0);
				if (soapchild.getProperty("Id").toString().equals("0")) {

				} else {
					loginUser = new LoginUser();
					loginUser.setUserid(soapchild.getProperty("Id").toString());
					loginUser.setType(soapchild.getProperty("Type").toString());
					loginUser.setSim(soapchild.getProperty("Sim").toString());
					loginUser.setName(soapchild.getProperty("Name").toString());
					loginUser.setPassword(soapchild.getProperty("Password")
							.toString());
					loginUser.setRealname(soapchild.getProperty("RealName")
							.toString());
					loginUser.setSex(soapchild.getProperty("Sex").toString());
					loginUser.setRemark(soapchild.getProperty("Remark")
							.toString());
					loginUser.setIslock(soapchild.getProperty("Islock")
							.toString());
					loginUser.setMark(soapchild.getProperty("Mark").toString());
					loginUser.setRewardmark(soapchild.getProperty("Rewardmark")
							.toString());
					loginUser.setPaidmark(soapchild.getProperty("Paidmark")
							.toString());
					loginUser.setHeadpic(soapchild.getProperty("HeadPic")
							.toString());
					loginUser.setImei(soapchild.getProperty("Imei").toString());
					loginUser.setProvince(soapchild.getProperty("Province")
							.toString());
					loginUser.setCity(soapchild.getProperty("City").toString());
					loginUser.setStock_age(soapchild.getProperty("StockAge")
							.toString());
					loginUser.setStock_style(soapchild
							.getProperty("StockStyle").toString());
					// loginUser.setCommfree(soapchild.getProperty("CommFree").toString());
					// loginUser.setInteractionfree(soapchild.getProperty("InteractionFree").toString());
				}
				soapRes.setObj(loginUser);
				soapRes.setCode(SOAP_UTILS.METHOD.USERINFOLOGIN);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USERINFOLOGIN);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userPayment(Object[] property_va) {
		String[] property_nm = { "userid", "pay" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USERPAYMENT);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj);
				soapRes.setCode(SOAP_UTILS.METHOD.USERPAYMENT);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {

			}
		});
	}

	@Override
	public void userRegistered(Object[] property_va) {
		String[] property_nm = { "sim", "name", "password", "realName", "sex",
				"imei", "province", "city", "stockAge", "stockStyle","code" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USERREGISTEREDV3);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("UserRegisteredV3Result"));
//				soapRes.setObj(obj.getProperty("UserRegisteredResult"));
				soapRes.setCode(SOAP_UTILS.METHOD.USERREGISTEREDV3);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USERREGISTEREDV3);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userPasswordReset(Object[] property_va) {
		String[] property_nm = { "username", "pwd_old", "pwd_new" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USERPASSWORDRESET);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("UserPasswordResetResult"));
				soapRes.setCode(SOAP_UTILS.METHOD.USERPASSWORDRESET);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USERPASSWORDRESET);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void opinion(Object[] property_va) {
		String[] property_nm = { "userid", "realname", "content" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.OPINION);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("OpinionResult"));
				soapRes.setCode(SOAP_UTILS.METHOD.OPINION);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.OPINION);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userNameCheck(Object[] property_va) {
		String[] property_nm = { "name" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USERNAMECHECK);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("UserNameCheckResult"));
				soapRes.setCode(SOAP_UTILS.METHOD.USERNAMECHECK);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USERNAMECHECK);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userEditor(Object[] property_va) {
		String[] property_nm = { "id", "options", "newInfo" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USEREDITOR);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("UserEditorResult"));
				soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userEditor_city(Object[] property_va) {
		String[] property_nm = { "id", "province", "city" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USEREDITOR_CITY);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("UserEditor_cityResult"));
				soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_CITY);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_CITY);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userEditor_head(Object[] property_va) {
		String[] property_nm = { "id", "images" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USEREDITOR_HEAD);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("UserEditor_headResult"));
				soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_HEAD);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_HEAD);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getReportList(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "fromUser", "pagesize", "pageindex" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETREPORTLIST);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				System.out.println("------getReportList = " + obj);
				SoapObject soapchids = (SoapObject) obj.getProperty(0);
				List<PublishMessage> list = new ArrayList<PublishMessage>();
				if (soapchids.getPropertyCount() != 0) {
					int item_count = soapchids.getPropertyCount();
					for (int i = 0; i < item_count; i++) {
						SoapObject soapchildsson = (SoapObject) soapchids
								.getProperty(i);
						PublishMessage bean = new PublishMessage();
						bean.setId(soapchildsson.getProperty("Id").toString());
						bean.setFromUser(soapchildsson.getProperty("FromUser")
								.toString());
						bean.setToUser(soapchildsson.getProperty("ToUser")
								.toString());
						bean.setPhoto(SOAP_UTILS.PIC_YANBAO
								+ soapchildsson.getProperty("Photo").toString());
						bean.setIsread(soapchildsson.getProperty("Isread")
								.toString());
						String Crtime = soapchildsson.getProperty("Crtime")
								.toString();
						String[] time_array = Crtime.split("T");
						Crtime = "";
						for(int k=0; k<time_array.length;k++){
							Crtime = Crtime + time_array[k] + " ";
						}
						bean.setCrtime(Crtime);
						bean.setAudio(soapchildsson.getProperty("Audio")
								.toString());
						bean.setAudioLength(soapchildsson.getProperty(
								"AudioLength").toString());
						bean.setDevType(soapchildsson.getProperty("DevType")
								.toString());
						bean.setBlogTitle(soapchildsson
								.getProperty("BlogTitle").toString());
						bean.setBlogAbstract(soapchildsson.getProperty(
								"BlogAbstract").toString());
						bean.setContent(soapchildsson.getProperty("Content")
								.toString());
						bean.setInfoType(soapchildsson.getProperty("InfoType")
								.toString());
						bean.setInfoDirection(soapchildsson.getProperty(
								"InfoDirection").toString());
						bean.setReportType(soapchildsson.getProperty(
								"ReportType").toString());
						bean.setInterTime(soapchildsson
								.getProperty("InterTime").toString());
						
						list.add(bean);
					}
				}
				soapRes.setObj(list);
				soapRes.setPage(isPage);
				soapRes.setCode(SOAP_UTILS.METHOD.GETREPORTLIST);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setPage(isPage);
				soapRes.setCode(SOAP_UTILS.METHOD.GETREPORTLIST);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void reportSubmit(Object[] property_va) {
		String[] property_nm = { "fromUser", "blogTitle", "blogAbstract",
				"content", "devType", "reportType", "images" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.REPORTSUBMIT);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				System.out.println("-----------reportSubmit =" + obj);
				soapRes.setObj(obj.getProperty("ReportSubmitResult"));
				soapRes.setCode(SOAP_UTILS.METHOD.REPORTSUBMIT);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.REPORTSUBMIT);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getReportContent(Object[] property_va) {
		String[] property_nm = { "id" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETREPORTCONTENT);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				SoapObject soapchildsson = (SoapObject) obj.getProperty(0);
				PublishMessageInfo info = new PublishMessageInfo();
				info.setId(soapchildsson.getProperty("Id").toString());
				info.setFromUser(soapchildsson.getProperty("FromUser")
						.toString());
				info.setToUser(soapchildsson.getProperty("ToUser").toString());
				info.setPhoto(SOAP_UTILS.PIC_YANBAO
						+ soapchildsson.getProperty("Photo").toString());
				info.setIsread(soapchildsson.getProperty("Isread").toString());
				String crtime =soapchildsson.getProperty("Crtime").toString();
				info.setCrtime(crtime);
				info.setAudio(soapchildsson.getProperty("Audio").toString());
				info.setAudioLength(soapchildsson.getProperty("AudioLength")
						.toString());
				info.setDevType(soapchildsson.getProperty("DevType").toString());
				info.setBlogTitle(soapchildsson.getProperty("BlogTitle")
						.toString());
				info.setBlogAbstract(soapchildsson.getProperty("BlogAbstract")
						.toString());
				info.setContent(soapchildsson.getProperty("Content").toString());
				info.setInfoType(soapchildsson.getProperty("InfoType")
						.toString());
				info.setInfoDirection(soapchildsson
						.getProperty("InfoDirection").toString());
				info.setReportType(soapchildsson.getProperty("ReportType")
						.toString());
				info.setInterTime(soapchildsson.getProperty("InterTime")
						.toString());
				soapRes.setObj(info);
				soapRes.setCode(SOAP_UTILS.METHOD.GETREPORTCONTENT);
				EventCache.commandActivity.post(soapRes);
				
				
				String[] time_array = crtime.split("T");
				crtime = "";
				for(int k=0; k<time_array.length;k++){
					crtime = crtime + time_array[k] + " ";
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETREPORTCONTENT);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void heartCountUpdate(Object[] property_va) {
		String[] property_nm = { "id", "heartcount" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.HEARTCOUNTUPDATE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				// SoapObject soapchildsson = (SoapObject) obj.getProperty(0);
				// HeartCountUpdateResult
				// PublishMessageInfo info = new PublishMessageInfo();
				// info.setId(soapchildsson.getProperty("Id").toString());
				System.out.println(">>>>obj" + obj);
				soapRes.setObj(obj.getProperty("HeartCountUpdateResult"));
				soapRes.setCode(SOAP_UTILS.METHOD.HEARTCOUNTUPDATE);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.HEARTCOUNTUPDATE);
				EventCache.commandActivity.post(soapRes);
			}
		});

	}

	@Override
	public void getOrderInfoService(Object[] property_va) {
		System.out.println("买买买1");
		String[] property_nm = { "alipaySn", "total", "storename" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETORDERINFO);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		System.out.println("买买买2");
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				// SoapObject soapchildsson = (SoapObject) obj.getProperty(0);
				// HeartCountUpdateResult
				// PublishMessageInfo info = new PublishMessageInfo();
				// info.setId(soapchildsson.getProperty("Id").toString());
				// System.out.println(">>>>obj" + obj);
				soapRes.setObj(obj.getProperty("MobileAlipayResult"));
				System.out.println(">>>> :　" + soapRes.getObj() + "");
				soapRes.setCode(SOAP_UTILS.METHOD.GETORDERINFO);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				System.out.println("买买买3");
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETORDERINFO);
				EventCache.commandActivity.post(soapRes);
			}
		});

	}

	@Override
	public void getCode(Object[] property_va) {
		String[] property_nm = { "sim" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETCODE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("SmsVerifyResult"));
				System.out.println(">>>>CODE  :　" + obj.toString());
				soapRes.setCode(SOAP_UTILS.METHOD.GETCODE);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				System.out.println("买买买3");
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCODE);
				EventCache.commandActivity.post(soapRes);
			}
		});

	}
	@Override
	public void SimVerify(Object[] property_va) {
		String[] property_nm = { "sim" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.SIMVERIFY);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {
			
			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("SimVerifyResult"));
				System.out.println(">>>>SimVerify  :　" + obj.toString());
				soapRes.setCode(SOAP_UTILS.METHOD.SIMVERIFY);
				EventCache.commandActivity.post(soapRes);
			}
			
			@Override
			public void soapError() {
				System.out.println("买买买3");
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.SIMVERIFY);
				EventCache.commandActivity.post(soapRes);
			}
		});
		
	}
	@Override
	public void CodeVerify(Object[] property_va) {
		String[] property_nm = { "sim","code" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.CODEVERIFY);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {
			
			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("CodeVerifyResult"));
				System.out.println(">>>>CodeVerify  :　" + obj.toString());
				soapRes.setCode(SOAP_UTILS.METHOD.CODEVERIFY);
				EventCache.commandActivity.post(soapRes);
			}
			
			@Override
			public void soapError() {
				System.out.println("买买买3");
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.CODEVERIFY);
				EventCache.commandActivity.post(soapRes);
			}
		});
		
	}
	@Override
	public void UserPasswordFind(Object[] property_va) {
		String[] property_nm = { "sim","pwd_new" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USERPASSWORDFIND);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {
			
			@Override
			public void soapResult(SoapObject obj) {
				soapRes.setObj(obj.getProperty("UserPasswordFindResult"));
				System.out.println(">>>>UserPasswordFind  :　" + obj.toString());
				soapRes.setCode(SOAP_UTILS.METHOD.USERPASSWORDFIND);
				EventCache.commandActivity.post(soapRes);
			}
			
			@Override
			public void soapError() {
				System.out.println("买买买3");
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USERPASSWORDFIND);
				EventCache.commandActivity.post(soapRes);
			}
		});
		
	}

	@Override
	public void getAnalystVideo(Object[] property_va) {
		String[] property_nm = { "analystid" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETANALYSTVIDEO);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
				SoapObject soapchilds = (SoapObject) obj.getProperty(0);
				List<AnalystVideo> list = new ArrayList<AnalystVideo>();
				for (int i = 0; i < soapchilds.getPropertyCount(); i++) {
					SoapObject soapchildsson = (SoapObject) soapchilds.getProperty(i);
					AnalystVideo info = new AnalystVideo();
					info.setId(soapchildsson.getProperty("Id").toString());
					info.setTitle(soapchildsson.getProperty("Title").toString());
					info.setPicture(soapchildsson.getProperty("Picture").toString());
					info.setContent(soapchildsson.getProperty("Content").toString());
					info.setAnalystid(soapchildsson.getProperty("Analystid").toString());
					info.setCrtime(soapchildsson.getProperty("Crtime").toString());
					list.add(info);
				}
				soapRes.setObj(list);
				soapRes.setCode(SOAP_UTILS.METHOD.GETANALYSTVIDEO);
				EventCache.commandActivity.post(soapRes);
				
				
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETANALYSTVIDEO);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}
	
		@Override
		public void getNewsTitleV2(Object[] property_va, final boolean isPage) {
			String[] property_nm = {  "pagesize", "pageindex" };
			asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETNEWSTITLEV2);
			asynTaskBase.setProperty_nm(property_nm);
			asynTaskBase.setProperty_va(property_va);
			asynTaskBase.executeDo(new SoapObjectResult() {

				@Override
				public void soapResult(SoapObject obj) {
					SoapObject soapchids = (SoapObject) obj.getProperty(0);
					List<FinNewsList> list = new ArrayList<FinNewsList>();
					if (soapchids.getPropertyCount() != 0) {
						int item_count = soapchids.getPropertyCount();
						for (int i = 0; i < item_count; i++) {
							SoapObject soapchildsson = (SoapObject) soapchids
									.getProperty(i);
							FinNewsList bean = new FinNewsList();
							bean.setId(soapchildsson.getProperty("Id").toString());
							bean.setCommentCount(soapchildsson.getProperty(
									"CommentCount").toString());
							bean.setIsRecommend(soapchildsson.getProperty(
									"IsRecommend").toString());
							bean.setCol(soapchildsson.getProperty("Col").toString());
							bean.setOrders(soapchildsson.getProperty("Orders")
									.toString());
							bean.setTitle(soapchildsson.getProperty("Title")
									.toString());
							bean.setThumbnail(SOAP_UTILS.PIC_YANBAO
									+ soapchildsson.getProperty("Thumbnail")
											.toString());
							bean.setSource(soapchildsson.getProperty("Source")
									.toString());
							bean.setAuthor(soapchildsson.getProperty("Author")
									.toString());
							bean.setPicture(SOAP_UTILS.PIC_YANBAO
									+ soapchildsson.getProperty("Picture")
											.toString());
							bean.setContent(soapchildsson.getProperty("Content")
									.toString());
							bean.setAdminid(soapchildsson.getProperty("Adminid")
									.toString());
							bean.setCrtime(soapchildsson.getProperty("Crtime")
									.toString());
							bean.setImportant(soapchildsson
									.getProperty("Important").toString());
							bean.setValue(soapchildsson.getProperty("Value")
									.toString());
							bean.setColTitle(soapchildsson.getProperty("ColTitle")
									.toString());
							list.add(bean);
						}
					}
					soapRes.setObj(list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETNEWSTITLEV2);
					EventCache.commandActivity.post(soapRes);
				}

				@Override
				public void soapError() {
					soapRes.setObj(null);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETNEWSTITLEV2);
					EventCache.commandActivity.post(soapRes);
				}
			});
		}


}
