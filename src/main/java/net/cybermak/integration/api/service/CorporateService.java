package net.cybermak.integration.api.service;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Value;
import com.bmc.thirdparty.org.apache.commons.codec.binary.Base64;
import net.cybermak.integration.api.model.TicketDetails;
import net.cybermak.integration.api.service.CorporateService;
import net.cybermak.integration.config.ReadProperties;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class CorporateService {

    static final Logger logger = LoggerFactory.getLogger(CorporateService.class);
    private static String OS = System.getProperty("os.name").toLowerCase();

    public String getBSSCorporateResponse(TicketDetails ticketDetails) throws java.text.ParseException {
        List<SubGroupInfo> groupInfoList = new ArrayList<>();
        String outputFlag = null;
        LocalDateTime startRunningDateTime = LocalDateTime.now();
        String certPath = null;
        try {
            int timeout = ticketDetails.getTimeOut();
            ReadProperties readProperties = ReadProperties.getInstance();
            //String integ_config_filePath = null;
            String integ_log_config_filePath = "D:\\Program Files\\BMC Software\\Integrations\\NetBeansProjects\\INTG_Config\\log4j.properties";
            PropertyConfigurator.configure(integ_log_config_filePath);
            //certPath = prop.get("Huawei_Certificate").toString();
            //System.setProperty("javax.net.ssl.trustStore", certPath);
            //System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

            logger.info("<------------------------------Executing Java Api----------------------------------->");

            String apiURL = "";
            String corporate_userName = null;
            String corporate_password = null;
            String servername = null;
            String userName = null;
            String userPassword = null;

            logger.info("*********************Start Getting Properties****************************");
            try {

                apiURL = readProperties.huaweiURL;
                logger.info("Corporate API URL : " + apiURL);
                corporate_userName = readProperties.huaweiUserName;
                corporate_password = readProperties.huaweiPassword;
                servername = readProperties.serverName;
                logger.info("ServerName :" + servername);
                userName = readProperties.userName;
                userPassword = readProperties.password;
                logger.info("Get properties successfully");

            } catch (Exception ex) {
                logger.error("ERROR READING CONF PROPERTIES" + ex);

            }

            logger.info("SOAP request Received Successfully");
            String corporateId = ticketDetails.getCorporateID();
            logger.info("Corporate Number : " + corporateId);
            String gUID = ticketDetails.getStagingGUID();
            logger.info("gUID : " + gUID);
            String company = ticketDetails.getCompany();
            logger.info("company : " + company);
            String consumerUnderCorporateFlag = ticketDetails.getConsumerUnderCorporateFlag();
            logger.info("consumerUnderCorporateFlag : " + consumerUnderCorporateFlag);

            String language = null;
            String customerSegment = null;
            String corporateGroupId = null;
            String groupName = null;
            String corporateName = null;
            String corporateType = null;
            String mainSegment = null;
            String spocName = null;
            String spocNumber = null;
            String registerDate = null;
            String customerLevel = null;
            String parentCorporationId = null;
            String faultCode = null;
            String faultString = null;
            String returnMsg = null;
            String returnCode = null;
            String spocEmailAddress = null;
            String serviceCenterName = null;
            String operatorName = null;
            String accountCode = null;
            String groupMemberLimit = null;
            String managerName = null;
            String managerEmployeeID = null;
            String manageType = null;
            String OfficePhoneNo = null;
            String idType = null;
            String idNumber = null;

            String fullAddress = "  ";
            String[] address = new String[20];
            Arrays.fill(address, "static"); //filling all values with static values

            String actionURL = "QueryCorporateInfo";
            String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v0=\"http://bes.huawei.com/interface/order-management/v0.1\">\n"
                    + "   <soapenv:Header/>\n"
                    + "   <soapenv:Body>\n"
                    + "      <v0:QueryCorporateInfoReqMsg>\n"
                    + "         <v0:reqHeader>\n"
                    + "            <v0:originChannel>774</v0:originChannel>\n"
                    + "            <v0:accessUser>" + corporate_userName + "</v0:accessUser>\n"
                    + "            <v0:accessPassword>" + corporate_password + "</v0:accessPassword>\n"
                    + "            <v0:operatorId>9061</v0:operatorId>\n"
                    + "         </v0:reqHeader>\n"
                    + "         <v0:reqBody>\n"
                    + "            <v0:corporationID>" + corporateId + "</v0:corporationID>\n"
                    + "         </v0:reqBody>\n"
                    + "      </v0:QueryCorporateInfoReqMsg>\n"
                    + "   </soapenv:Body>\n"
                    + "</soapenv:Envelope>";


            logger.info("--------------------Calling Web Service starts---------------------");
            logger.info("---------------------------------------Request xml-------------------------------------\n");
            logger.info("\n");
            logger.info(request);
            logger.info("\n");
            HttpPost httppost = new HttpPost(apiURL);
            StringEntity stringentity = null;
            stringentity = new StringEntity(request, "text/xml", "UTF-8");
            stringentity.setChunked(true);
            httppost.addHeader("Accept", "application/soap+xml");
            httppost.addHeader("SOAPAction", actionURL);
            httppost.addHeader("Content-Type", "application/xml");
            httppost.setEntity(stringentity);
            HttpClient httpclient = new DefaultHttpClient();
            HttpParams httpParams = httpclient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, timeout); // http.connection.timeout
            HttpConnectionParams.setSoTimeout(httpParams, timeout); // http.socket.timeout
            HttpResponse response = null;
            try {
                response = httpclient.execute(httppost);
                logger.info("Response : " + response);
                HttpEntity entity = response.getEntity();
                String strresponse = null;
                logger.info("Entity :" + entity);
                if (entity != null) {
                    strresponse = EntityUtils.toString(entity);
                    logger.info("\n");
                    logger.info("Web-Service Output : " + strresponse);
                    logger.info("\n");
                    try {
                        faultCode = strresponse.substring(strresponse.indexOf("<faultcode>") + 11, strresponse.indexOf("</faultcode>"));
                        logger.error("Fault Code : " + faultCode);
                    } catch (Exception ex) {
                        logger.error("Exception while fetching Fault Code" + ex);
                    }
                    try {
                        faultString = strresponse.substring(strresponse.indexOf("<faultstring xml:lang=\"en\">") + 27, strresponse.indexOf("</faultstring>"));
                        logger.error("Fault String : " + faultString);
                    } catch (Exception ex) {
                        logger.error("Exception while fetching Fault Message" + ex);
                    }
                    if (faultString == null) {

                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        ByteArrayInputStream input2 = new ByteArrayInputStream(strresponse.toString().getBytes("UTF-8"));
                        Document doc = builder.parse(input2);
                        NodeList list7 = doc.getElementsByTagName("ns4:rspHeader");
                        Node n7 = list7.item(0);
                        Element e7 = (Element) n7;

                        try {
                            try {
                                returnCode = e7.getElementsByTagName("ns4:returnCode").item(0).getTextContent();
                                logger.info("Return Code : " + returnCode);
                            } catch (Exception ex) {
                                logger.error("Error while fetching success return code (ns4): " + ex);
                            }
                            try {
                                returnMsg = e7.getElementsByTagName("ns4:returnMsg").item(0).getTextContent();
                                logger.info("Return Message : " + returnMsg);
                            } catch (Exception ex) {
                                logger.error("Error while fetching failure return code (ns4): " + ex);
                            }
                        } catch (Exception ex) {
                            logger.error("Exception while Fetching Failure Response Header (ns4): " + ex);
                        }

                        NodeList list = doc.getElementsByTagName("ns0:rspHeader");

                        try {
                            Node n = list.item(0);
                            Element e = (Element) n;
                            try {
                                returnCode = e.getElementsByTagName("ns0:returnCode").item(0).getTextContent();
                                logger.info("Return Code : " + returnCode);
                            } catch (Exception ex) {
                                logger.error("Error while fetching success return code (ns0): " + ex);
                            }
                            try {
                                returnMsg = e.getElementsByTagName("ns0:returnMsg").item(0).getTextContent();
                                logger.info("Return Message : " + returnMsg);
                            } catch (Exception ex) {
                                logger.error("Error while fetching failure return code (ns0): " + ex);
                            }
                        } catch (Exception ex) {
                            logger.error("Exception while Fetching Success Response Header (ns0): " + ex);
                        }
                        NodeList list1 = doc.getElementsByTagName("ns1:rspHeader");

                        try {
                            Node n1 = list1.item(0);
                            Element e1 = (Element) n1;
                            try {
                                returnCode = e1.getElementsByTagName("ns1:returnCode").item(0).getTextContent();
                                logger.info("Return Code : " + returnCode);
                            } catch (Exception ex) {
                                logger.error("Error while fetching failure return code (ns1): " + ex);
                            }
                            try {
                                returnMsg = e1.getElementsByTagName("ns1:returnMsg").item(0).getTextContent();
                                logger.info("Return Message : " + returnMsg);
                            } catch (Exception ex) {
                                logger.error("Error while fetching failure return message (ns1): " + ex);
                            }
                        } catch (Exception ex) {
                            logger.error("Exception while Fetching Failed Response Header (ns1): " + ex);
                        }
                        if (Objects.equals(returnCode, "0")) {

                            try {
                                NodeList list2 = doc.getElementsByTagName("ns0:rspBody");
                                Node n2 = list2.item(0);
                                Element e2 = (Element) n2;
                                try {
                                    corporateName = e2.getElementsByTagName("ns0:corporateName").item(0).getTextContent();
                                    //logger.info("Corporate Name : "+corporateName);
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Corporate Name : " + ex);
                                }
                                try {
                                    corporateType = e2.getElementsByTagName("ns0:corporateType").item(0).getTextContent();
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Corporate Type : " + ex);
                                }
                                try {
                                    parentCorporationId = e2.getElementsByTagName("ns0:parentCoporationID").item(0).getTextContent();
                                    //  logger.info("Upper Level Corporate Customer : "+parentCorporationId);
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Upper Level Corporate Customer : " + ex);
                                }
                                try {
                                    customerLevel = e2.getElementsByTagName("ns0:customerLevel").item(0).getTextContent();
                                    //logger.info("Customer Level : "+customerLevel);
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Customer Level : " + ex);
                                }
                                try {
                                    customerSegment = e2.getElementsByTagName("ns0:customerSegment").item(0).getTextContent();
                                    //logger.info("Customer Segment : "+customerSegment);
                                } catch (Exception ex) {
                                    logger.error("Error while fetching Customer Segment : " + ex);
                                }
                                try {
                                    registerDate = e2.getElementsByTagName("ns0:registerDate").item(0).getTextContent();
                                    //logger.info("Register Date : "+registerDate);
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Register Date : " + ex);
                                }
                                try {
                                    mainSegment = e2.getElementsByTagName("ns0:MainSegment").item(0).getTextContent();
                                    //logger.info("Register Date : "+registerDate);
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Main Segment : " + ex);
                                }
                                try {
                                    spocNumber = e2.getElementsByTagName("ns0:SPOCNumber").item(0).getTextContent();
                                    //logger.info("SPOC Number : "+spocNumber);
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching SPOC Number : " + ex);
                                }
                                try {
                                    spocName = e2.getElementsByTagName("ns0:SPOCName").item(0).getTextContent();
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching SPOC Name : " + ex);
                                }
                                try {
                                    spocEmailAddress = e2.getElementsByTagName("ns0:SPOCEmailAddress").item(0).getTextContent();
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching SPOC Email Address : " + ex);
                                }
                                try {
                                    language = e2.getElementsByTagName("ns0:language").item(0).getTextContent();
                                    //logger.info("Language : "+language);
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Language : " + ex);
                                }
                                try {
                                    serviceCenterName = e2.getElementsByTagName("ns0:ServiceCenterName").item(0).getTextContent();
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Service Center Name : " + ex);
                                }
                                try {
                                    operatorName = e2.getElementsByTagName("ns0:OperatorName").item(0).getTextContent();
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Operator Name : " + ex);
                                }
                            } catch (Exception ex) {
                                logger.error("Error while fetching Response Body Parameters");
                            }
                            try {
                                NodeList list3 = doc.getElementsByTagName("ns0:GroupSubInfo");
                                logger.info("Length of GroupSubInfo List = " + list3.getLength());
                                for (int i = 0; i < list3.getLength(); i++) {
                                    if (list3.item(i).getParentNode().getNodeName().equals("ns0:rspBody")) {
                                        Node n3 = list3.item(i);
                                        if (n3.getNodeType() == Node.ELEMENT_NODE) {
                                            Element e3 = (Element) n3;
                                            try {
                                                SubGroupInfo subGroup = new SubGroupInfo();
                                                subGroup.setAccountCode((e3.getElementsByTagName("ns0:accountCode").item(0).getTextContent() == null) ? "" : e3.getElementsByTagName("ns0:accountCode").item(0).getTextContent());
                                                subGroup.setCorporateGroupID((e3.getElementsByTagName("ns0:corporateGroupID").item(0).getTextContent() == null) ? "" : e3.getElementsByTagName("ns0:corporateGroupID").item(0).getTextContent());
                                                subGroup.setGroupName((e3.getElementsByTagName("ns0:GroupName").item(0).getTextContent() == null) ? "" : e3.getElementsByTagName("ns0:GroupName").item(0).getTextContent());
                                                subGroup.setGroupMemberLimit((e3.getElementsByTagName("ns0:GroupMemberLimit").item(0).getTextContent()) == null ? "" : e3.getElementsByTagName("ns0:GroupMemberLimit").item(0).getTextContent());
                                                groupInfoList.add(subGroup);
                                            } catch (Exception e) {
                                                logger.info(e.getMessage());
                                            }
                                        }
                                    }
                                }
                            } catch (Exception ex) {
                                logger.error("Exception while fetching Group Info : " + ex);
                            }
                            try {
                                NodeList list4 = doc.getElementsByTagName("ns0:managerInfo");
                                Node n4 = list4.item(0);
                                Element e4 = (Element) n4;
                                try {
                                    managerName = e4.getElementsByTagName("ns0:managerName").item(0).getTextContent();
                                    //logger.info("Corporate Group ID : "+corporateGroupId);
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Manager Name : " + ex);
                                }
                                try {
                                    managerEmployeeID = e4.getElementsByTagName("ns0:managerEmployeeID").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Manager Employee ID");
                                }
                                try {
                                    manageType = e4.getElementsByTagName("ns0:manageType").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Manage Type");
                                }
                                try {
                                    OfficePhoneNo = e4.getElementsByTagName("ns0:OfficePhoneNo").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching Office Phone No");
                                }

                            } catch (Exception ex) {
                                logger.error("Exception while fetching Manager Info : " + ex);
                            }
                            try {
                                NodeList list5 = doc.getElementsByTagName("ns0:certificateInfo");
                                Node n5 = list5.item(0);
                                Element e5 = (Element) n5;
                                try {
                                    idType = e5.getElementsByTagName("ns0:idType").item(0).getTextContent();
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching ID Type : " + ex);
                                }
                                try {
                                    idNumber = e5.getElementsByTagName("ns0:idNumber").item(0).getTextContent();
                                } catch (Exception ex) {
                                    logger.error("Exception while fetching ID Number");
                                }

                            } catch (Exception ex) {
                                logger.error("Exception while fetching Certificate Info : " + ex);
                            }
                            try {
                                NodeList list6 = doc.getElementsByTagName("ns0:corporateAddress");
                                Node n6 = list6.item(0);
                                Element e6 = (Element) n6;

                                try {
                                    address[1] = e6.getElementsByTagName("Address1").item(0).getTextContent();
                                    //logger.info("Corporate Group ID : "+corporateGroupId);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 1 : " + e);
                                }
                                try {
                                    address[2] = e6.getElementsByTagName("Address2").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 2");
                                }
                                try {
                                    address[3] = e6.getElementsByTagName("Address3").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 3");
                                }
                                try {
                                    address[4] = e6.getElementsByTagName("Address4").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 4");
                                }
                                try {
                                    address[5] = e6.getElementsByTagName("Address5").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 5");
                                }
                                try {
                                    address[6] = e6.getElementsByTagName("Address6").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 6");
                                }
                                try {
                                    address[7] = e6.getElementsByTagName("Address7").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 7");
                                }
                                try {
                                    address[8] = e6.getElementsByTagName("Address8").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 8");
                                }
                                try {
                                    address[9] = e6.getElementsByTagName("Address9").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 9");
                                }
                                try {
                                    address[10] = e6.getElementsByTagName("Address10").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 10");
                                }
                                try {
                                    address[11] = e6.getElementsByTagName("Address11").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 11");
                                }
                                try {
                                    address[12] = e6.getElementsByTagName("Address12").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 12");
                                }
                                try {
                                    address[13] = e6.getElementsByTagName("Address13").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 13");
                                }
                                try {
                                    address[14] = e6.getElementsByTagName("Address14").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 14");
                                }
                                try {
                                    address[15] = e6.getElementsByTagName("Address15").item(0).getTextContent();
                                    //logger.info("Corporate Group Name : "+groupName);
                                } catch (Exception e) {
                                    logger.error("Exception while fetching Address 15");
                                }

                            } catch (Exception ex) {
                                logger.error("Exception while fetching Corporate Address : " + ex);
                            }
                            outputFlag = "Success";
                            System.out.println("Success");
                            logger.info("Values fetched Successfully");
                        } else if (!returnCode.equals("0")) {
                            System.out.println("Failed : " + returnMsg);
                            logger.error("Failed : " + returnMsg);
                            outputFlag = "Failed";
                        }

                    } else {
                        System.out.println("Failed:" + faultString);
                        logger.error("Failed :" + faultString);
                        outputFlag = "Failed";

                        if ((faultCode != null || faultString != null) && (returnMsg == null)) {
                            returnCode = faultCode;
                            returnMsg = faultString;
                        }
                    }
                } else {
                    System.out.println("Failed");
                    logger.error("Entity is NULL");
                    outputFlag = "Failed";
                }

                logger.info("Started handling address");
                fullAddress = handleAddress(address);

                try {

                    ARServerUser ct = loginToRemedy(servername, userName, userPassword, 6000);
                    create(ct, corporateId, gUID, language, customerSegment, corporateName, spocName, spocNumber, registerDate, customerLevel, parentCorporationId, returnMsg, corporateType, mainSegment, managerName, managerEmployeeID, manageType, OfficePhoneNo, idType, idNumber, spocEmailAddress, fullAddress, operatorName, serviceCenterName, returnCode, company, consumerUnderCorporateFlag);
                    createRecordInCorporateNewForm(ct, groupInfoList, corporateId, gUID);
                    logMeOut(ct);

                } catch (Exception e) {
                    System.out.println("Failed");
                    logger.error("Error in connection :" + e);
                    outputFlag = "Failed";
                }
            }catch (Exception e) {
                returnMsg = e.getMessage();
                ARServerUser ct = loginToRemedy(servername, userName, userPassword, 6000);
                create(ct, corporateId, gUID, null, null, null, null, null,
                        null, null, null, returnMsg, null, null,
                        null, null, null, null, null, null,
                        null, null, null, null, "500", company, consumerUnderCorporateFlag);
                createRecordInCorporateNewForm(ct, null, corporateId, gUID);
                logMeOut(ct);
                logger.error("Exception : " + e.getMessage());
                outputFlag = "Failed";
            }

        } catch (Exception e) {

            System.out.println("Failed");
            logger.error(e.toString());
            outputFlag = "Failed";

        }
        LocalDateTime endRunningDateTime = LocalDateTime.now();
        logger.info("Application finished running at " + endRunningDateTime);
        consumedTimeMethod(ticketDetails.getStagingGUID(), startRunningDateTime, endRunningDateTime);

        return outputFlag;

    }

    private static void create(ARServerUser ct, String corporateId, String gUID, String language, String customerSegment, String corporateName, String spocName, String spocNumber, String registerDate, String customerLevel, String parentCorporationId, String returnMsg, String corporateType, String mainSegment, String managerName, String managerEmployeeID, String manageType, String OfficePhoneNo, String idType, String idNumber, String spocEmailAddress, String fullAddress, String operatorName, String serviceCenterName, String returnCode, String company, String consumerUnderCorporateFlag) {
        logger.info("---------Start Pushing data to Remedy-----------");
        try {
            String schema = "INTG:BSS:UserInfo:StorageForm";

            Entry entry = new Entry();
            entry.put(536870961, new Value(corporateId));
            entry.put(536870955, new Value(gUID));
            entry.put(536870921, new Value(language));
//            entry.put(536870924, new Value(customerSegment));
//            entry.put(715032302, new Value(mainSegment));
            entry.put(536870924, new Value(mainSegment));
//            entry.put(536870927, new Value(corporateGroupId));
//            entry.put(536870928, new Value(groupName));
            entry.put(536870931, new Value(corporateName));
            entry.put(536870933, new Value(spocName));
            entry.put(536870934, new Value(spocNumber));
            entry.put(536870935, new Value(registerDate));
            entry.put(536870949, new Value(customerLevel));
            entry.put(536870932, new Value(parentCorporationId));
            entry.put(536870936, new Value("active"));
            entry.put(922060602, new Value(returnMsg));
            entry.put(715032301, new Value(corporateType));
            entry.put(715032303, new Value(managerName));
            entry.put(715032304, new Value(managerEmployeeID));
            entry.put(715032305, new Value(manageType));
            entry.put(715032306, new Value(OfficePhoneNo));
            entry.put(715032307, new Value(idType));
            entry.put(715032308, new Value(idNumber));
            entry.put(715032309, new Value(spocEmailAddress));
//            entry.put(715032310, new Value(accountCode));
//            entry.put(715032311, new Value(groupMemberLimit));
            entry.put(536870916, new Value(fullAddress));
            entry.put(922061201, new Value(operatorName));
            entry.put(536870940, new Value(serviceCenterName));
            entry.put(922061701, new Value(returnCode));
            entry.put(922061501, new Value(company));
            entry.put(709042301, new Value(consumerUnderCorporateFlag));

            String result = ct.createEntry(schema, entry);
            logger.info("Final Result :" + result);
        } catch (ARException e) {
            System.out.println(e.getMessage());
            logger.error("---Error while Pushing data to remedy---" + e.getMessage());
        }
    }

    public static void createRecordInCorporateNewForm(ARServerUser ct, List<SubGroupInfo> subGroups, String corporateId, String gUID) {
        logger.info("---------Start Pushing data to Remedy to GroupSubInfoList-----------");
        try {
            String newSchema = "INTG:Parsing_Corporate_GroupSubInfoList";
            logger.info("No of displayed Lists = " + subGroups.size());
            for (int i = 0; i < subGroups.size(); i++) {
                SubGroupInfo group = subGroups.get(i);
                Entry entry = new Entry();
                entry.put(722032301, new Value(group.getAccountCode()));
                entry.put(722032302, new Value(group.getCorporateGroupID()));
                entry.put(722032303, new Value(group.getGroupName()));
                entry.put(722032304, new Value(group.getGroupMemberLimit()));
                entry.put(536870961, new Value(corporateId));
                entry.put(536870935, new Value(gUID));

                String result2 = ct.createEntry(newSchema, entry);
                logger.info("Final Result for Group Sub Info List:" + result2);

            }
        } catch (ARException e) {
            System.out.println(e.getMessage());
            logger.error("---Error while Pushing data to remedy to GroupSubInfoList---" + e.getMessage());
        }

    }
    public static ARServerUser loginToRemedy(String servername, String userName, String userPassword, int port) {
        byte[] bytesEncoded = Base64.encodeBase64(userPassword.getBytes());
        ARServerUser ct = new ARServerUser();
        ct.setServer(servername);
        ct.setUser(userName);
        ct.setPassword(userPassword);
        ct.setPort(port);
        logger.info("Server Name :" + servername + " ----User Name :" + userName + " ----Password :" + bytesEncoded + " ----Port :" + port);
        logger.info("Connected------User verified");
        logger.info("----Start verifying the AR User----");
        try {
            ct.verifyUser();
            logger.info("Verified the AR user successfully.");
        } catch (ARException e) {
            logger.error("Error while verifying the AR user :" + e.getMessage());

        }
        return ct;
    }

    public static void logMeOut(ARServerUser ct) {
        ct.logout();
    }


    public static void consumedTimeMethod(String GUID, LocalDateTime startRunningDateTime, LocalDateTime endRunningDateTime) throws java.text.ParseException {
        try {
            long differenceInNano = ChronoUnit.NANOS.between(startRunningDateTime, endRunningDateTime);
            long differenceInSeconds = ChronoUnit.SECONDS.between(startRunningDateTime, endRunningDateTime);
            long differenceInMilli = ChronoUnit.MILLIS.between(startRunningDateTime, endRunningDateTime);
            long differenceInMinutes = ChronoUnit.MINUTES.between(startRunningDateTime, endRunningDateTime);
            long differenceInHours = ChronoUnit.HOURS.between(startRunningDateTime, endRunningDateTime);

            logger.info("====================== The consumed time to run this api ====================== ");
            logger.info("Consumed Time : " + differenceInHours + " hours, " + differenceInMinutes + " minutes, " + differenceInSeconds + " seconds, " + differenceInMilli + " milliseconds, " + differenceInNano + " microseconds for Ticket GUID : " + GUID);

        } catch (ParseException e) {
            logger.info("Error while parsing the consumed time: " + e.toString());
        }
    }

    public String handleAddress(String[] address) {
        String fullAddress = null;
        for (int i = 1; i <= address.length; i++) {
            if (address[i] != "static") {

                fullAddress = fullAddress + address[i] + ", ";

            }
        }
        String partialString = fullAddress.substring(0, fullAddress.length() - 2);
        fullAddress = partialString + ".";

        if (fullAddress.charAt(0) == '.') {
            fullAddress = "";
        } else {
            fullAddress = partialString + ".";
            fullAddress = fullAddress.substring(2, fullAddress.length());
        }
        logger.info("Full address : " + fullAddress);
        return fullAddress;
    }


}