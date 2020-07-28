package com.nineclient.web;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import com.nineclient.model.PubOperator;
import com.nineclient.model.PubOrganization;
import com.nineclient.model.UmpAuthority;
import com.nineclient.model.UmpBrand;
import com.nineclient.model.UmpBusinessType;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpConfig;
import com.nineclient.model.UmpDictionary;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.UmpParentBusinessType;
import com.nineclient.model.UmpProduct;
import com.nineclient.model.UmpRole;
import com.nineclient.model.UmpVersion;
import com.nineclient.model.VocAccount;
import com.nineclient.model.VocAppkey;
import com.nineclient.model.VocBrand;
import com.nineclient.model.VocComment;
import com.nineclient.model.VocCommentCategory;
import com.nineclient.model.VocCommentLevelCategory;
import com.nineclient.model.VocCommentLevelRule;
import com.nineclient.model.VocEmail;
import com.nineclient.model.VocGoods;
import com.nineclient.model.VocGoodsProperty;
import com.nineclient.model.VocShop;
import com.nineclient.model.VocSku;
import com.nineclient.model.VocTemplate;
import com.nineclient.model.VocTemplateCategory;
import com.nineclient.model.VocWordCategory;
import com.nineclient.model.VocWordExpressions;
import com.nineclient.model.WccActivities;
import com.nineclient.model.WccAnswer;
import com.nineclient.model.WccAutokbs;
import com.nineclient.model.WccAwardInfo;
import com.nineclient.model.WccChatRecourds;
import com.nineclient.model.WccComment;
import com.nineclient.model.WccCommunity;
import com.nineclient.model.WccContent;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccGroup;
import com.nineclient.model.WccGroupMessFriend;
import com.nineclient.model.WccGroupMessage;
import com.nineclient.model.WccInterActiveApp;
import com.nineclient.model.WccLeavemsgRecord;
import com.nineclient.model.WccLotteryActivity;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccMembershipLevel;
//import com.nineclient.model.WccMenu;
import com.nineclient.model.WccMessage;
import com.nineclient.model.WccOffcAtivity;
import com.nineclient.model.WccOption;
import com.nineclient.model.WccPlate;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccPraise;
import com.nineclient.model.WccQrcode;
import com.nineclient.model.WccQuestion;
import com.nineclient.model.WccQuestionnaire;
import com.nineclient.model.WccSncode;
import com.nineclient.model.WccStore;
import com.nineclient.model.WccTopic;
import com.nineclient.model.WccUser;
import com.nineclient.model.WccUserFieldValue;
import com.nineclient.model.WccUserFields;
import com.nineclient.model.WccUserLottery;
import com.nineclient.model.WccUserName;
import com.nineclient.model.WccUserPagetemp;
import com.nineclient.model.WccUtility;
import com.nineclient.model.WccWelcomkbs;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<UmpAuthority, String> getUmpAuthorityToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.UmpAuthority, java.lang.String>() {
            public String convert(UmpAuthority umpAuthority) {
                return new StringBuilder().append(umpAuthority.getParentId()).append(' ').append(umpAuthority.getDisplayName()).append(' ').append(umpAuthority.getUrl()).append(' ').append(umpAuthority.getImage()).toString();
            }
        };
    }

	public Converter<Long, UmpAuthority> getIdToUmpAuthorityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.UmpAuthority>() {
            public com.nineclient.model.UmpAuthority convert(java.lang.Long id) {
                return UmpAuthority.findUmpAuthority(id);
            }
        };
    }

	public Converter<String, UmpAuthority> getStringToUmpAuthorityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.UmpAuthority>() {
            public com.nineclient.model.UmpAuthority convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UmpAuthority.class);
            }
        };
    }

	public Converter<UmpBrand, String> getUmpBrandToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.UmpBrand, java.lang.String>() {
            public String convert(UmpBrand umpBrand) {
                return new StringBuilder().append(umpBrand.getBrandName()).append(' ').append(umpBrand.getCreateTime()).append(' ').append(umpBrand.getRemark()).toString();
            }
        };
    }

	public Converter<Long, UmpBrand> getIdToUmpBrandConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.UmpBrand>() {
            public com.nineclient.model.UmpBrand convert(java.lang.Long id) {
                return UmpBrand.findUmpBrand(id);
            }
        };
    }

	public Converter<String, UmpBrand> getStringToUmpBrandConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.UmpBrand>() {
            public com.nineclient.model.UmpBrand convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UmpBrand.class);
            }
        };
    }

	public Converter<UmpBusinessType, String> getUmpBusinessTypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.UmpBusinessType, java.lang.String>() {
            public String convert(UmpBusinessType umpBusinessType) {
                return new StringBuilder().append(umpBusinessType.getBusinessName()).append(' ').append(umpBusinessType.getSort()).append(' ').append(umpBusinessType.getRemark()).append(' ').append(umpBusinessType.getCreateTime()).toString();
            }
        };
    }

	public Converter<Long, UmpBusinessType> getIdToUmpBusinessTypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.UmpBusinessType>() {
            public com.nineclient.model.UmpBusinessType convert(java.lang.Long id) {
                return UmpBusinessType.findUmpBusinessType(id);
            }
        };
    }

	public Converter<String, UmpBusinessType> getStringToUmpBusinessTypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.UmpBusinessType>() {
            public com.nineclient.model.UmpBusinessType convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UmpBusinessType.class);
            }
        };
    }

	public Converter<UmpChannel, String> getUmpChannelToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.UmpChannel, java.lang.String>() {
            public String convert(UmpChannel umpChannel) {
                return new StringBuilder().append(umpChannel.getChannelName()).append(' ').append(umpChannel.getRemark()).append(' ').append(umpChannel.getCreateTime()).toString();
            }
        };
    }

	public Converter<Long, UmpChannel> getIdToUmpChannelConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.UmpChannel>() {
            public com.nineclient.model.UmpChannel convert(java.lang.Long id) {
                return UmpChannel.findUmpChannel(id);
            }
        };
    }

	public Converter<String, UmpChannel> getStringToUmpChannelConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.UmpChannel>() {
            public com.nineclient.model.UmpChannel convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UmpChannel.class);
            }
        };
    }

	public Converter<UmpCompany, String> getUmpCompanyToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.UmpCompany, java.lang.String>() {
            public String convert(UmpCompany umpCompany) {
                return new StringBuilder().append(umpCompany.getName()).toString();//.append(' ').append(umpCompany.getCreateTime()).append(' ').append(umpCompany.getCompanyCode()).append(' ').append(umpCompany.getMaxAccount()).toString();
            }
        };
    }

	public Converter<Long, UmpCompany> getIdToUmpCompanyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.UmpCompany>() {
            public com.nineclient.model.UmpCompany convert(java.lang.Long id) {
                return UmpCompany.findUmpCompany(id);
            }
        };
    }

	public Converter<String, UmpCompany> getStringToUmpCompanyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.UmpCompany>() {
            public com.nineclient.model.UmpCompany convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UmpCompany.class);
            }
        };
    }

	public Converter<UmpDictionary, String> getUmpDictionaryToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.UmpDictionary, java.lang.String>() {
            public String convert(UmpDictionary umpDictionary) {
                return new StringBuilder().append(umpDictionary.getTypeCode()).append(' ').append(umpDictionary.getTypeName()).append(' ').append(umpDictionary.getTypeTitle()).append(' ').append(umpDictionary.getTypeTitleCh()).toString();
            }
        };
    }

	public Converter<Long, UmpDictionary> getIdToUmpDictionaryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.UmpDictionary>() {
            public com.nineclient.model.UmpDictionary convert(java.lang.Long id) {
                return UmpDictionary.findUmpDictionary(id);
            }
        };
    }

	public Converter<String, UmpDictionary> getStringToUmpDictionaryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.UmpDictionary>() {
            public com.nineclient.model.UmpDictionary convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UmpDictionary.class);
            }
        };
    }

	public Converter<UmpOperator, String> getUmpOperatorToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.UmpOperator, java.lang.String>() {
            public String convert(UmpOperator umpOperator) {
                return new StringBuilder().append(umpOperator.getEmail()).append(' ').append(umpOperator.getOperatorName()).append(' ').append(umpOperator.getAccount()).append(' ').append(umpOperator.getPassword()).toString();
            }
        };
    }

	public Converter<Long, UmpOperator> getIdToUmpOperatorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.UmpOperator>() {
            public com.nineclient.model.UmpOperator convert(java.lang.Long id) {
                return UmpOperator.findUmpOperator(id);
            }
        };
    }

	public Converter<String, UmpOperator> getStringToUmpOperatorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.UmpOperator>() {
            public com.nineclient.model.UmpOperator convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UmpOperator.class);
            }
        };
    }
	public Converter<UmpConfig, String> getUmpConfigToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.UmpConfig, java.lang.String>() {
            public String convert(UmpConfig umpConfig) {
                return new StringBuilder().append(umpConfig.getName()).append(' ').append(umpConfig.getKeyValue()).append(' ').append(umpConfig.getRemarks()).toString();
            }
        };
    }

	public Converter<Long, UmpConfig> getIdToUmpConfigConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.UmpConfig>() {
            public com.nineclient.model.UmpConfig convert(java.lang.Long id) {
                return UmpConfig.findUmpConfig(id);
            }
        };
    }

	public Converter<String, UmpConfig> getStringToUmpConfigConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.UmpConfig>() {
            public com.nineclient.model.UmpConfig convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UmpConfig.class);
            }
        };
    }
//	public Converter<UmpProduct, String> getUmpProductToStringConverter() {
//        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.UmpProduct, java.lang.String>() {
//            public String convert(UmpProduct umpProduct) {
//                return new StringBuilder().append(umpProduct.getProductName()).toString();
//            }
//        };
//    }
//
//	public Converter<Long, UmpProduct> getIdToUmpProductConverter() {
//        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.UmpProduct>() {
//            public com.nineclient.model.UmpProduct convert(java.lang.Long id) {
//                return UmpProduct.findUmpProduct(id);
//            }
//        };
//    }
//
//	public Converter<String, UmpProduct> getStringToUmpProductConverter() {
//        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.UmpProduct>() {
//            public com.nineclient.model.UmpProduct convert(String id) {
//                return getObject().convert(getObject().convert(id, Long.class), UmpProduct.class);
//            }
//        };
//    }

	public Converter<UmpRole, String> getUmpRoleToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.UmpRole, java.lang.String>() {
            public String convert(UmpRole umpRole) {
                return new StringBuilder().append(umpRole.getRoleName()).toString();//.append(' ').append(umpRole.getRemark()).append(' ').append(umpRole.getStartTime()).append(' ').append(umpRole.getCreateTime()).toString();
            }
        };
    }

	public Converter<Long, UmpRole> getIdToUmpRoleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.UmpRole>() {
            public com.nineclient.model.UmpRole convert(java.lang.Long id) {
                return UmpRole.findUmpRole(id);
            }
        };
    }

	public Converter<String, UmpRole> getStringToUmpRoleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.UmpRole>() {
            public com.nineclient.model.UmpRole convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UmpRole.class);
            }
        };
    }


	public Converter<UmpVersion, String> getUmpVersionToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.UmpVersion, java.lang.String>() {
            public String convert(UmpVersion umpVersion) {
                return new StringBuilder().append(umpVersion.getRemark()).toString();
            }
        };
    }

	public Converter<Long, UmpVersion> getIdToUmpVersionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.UmpVersion>() {
            public com.nineclient.model.UmpVersion convert(java.lang.Long id) {
                return UmpVersion.findUmpVersion(id);
            }
        };
    }

	public Converter<String, UmpVersion> getStringToUmpVersionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.UmpVersion>() {
            public com.nineclient.model.UmpVersion convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UmpVersion.class);
            }
        };
    }

	public Converter<WccActivities, String> getWccActivitiesToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccActivities, java.lang.String>() {
            public String convert(WccActivities wccActivities) {
                return new StringBuilder().append(wccActivities.getActivitiesName()).append(' ').append(wccActivities.getActivitiesTopic()).append(' ').append(wccActivities.getBeginTime()).append(' ').append(wccActivities.getEndTime()).toString();
            }
        };
    }

	public Converter<Long, WccActivities> getIdToWccActivitiesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccActivities>() {
            public com.nineclient.model.WccActivities convert(java.lang.Long id) {
                return WccActivities.findWccActivities(id);
            }
        };
    }

	public Converter<String, WccActivities> getStringToWccActivitiesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccActivities>() {
            public com.nineclient.model.WccActivities convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccActivities.class);
            }
        };
    }

	public Converter<WccAnswer, String> getWccAnswerToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccAnswer, java.lang.String>() {
            public String convert(WccAnswer wccAnswer) {
                return new StringBuilder().append(wccAnswer.getAnswer()).append(' ').append(wccAnswer.getUserName()).append(' ').append(wccAnswer.getUserPhone()).toString();
            }
        };
    }

	public Converter<Long, WccAnswer> getIdToWccAnswerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccAnswer>() {
            public com.nineclient.model.WccAnswer convert(java.lang.Long id) {
                return WccAnswer.findWccAnswer(id);
            }
        };
    }

	public Converter<String, WccAnswer> getStringToWccAnswerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccAnswer>() {
            public com.nineclient.model.WccAnswer convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccAnswer.class);
            }
        };
    }

	public Converter<WccAutokbs, String> getWccAutokbsToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccAutokbs, java.lang.String>() {
            public String convert(WccAutokbs wccAutokbs) {
                return new StringBuilder().append(wccAutokbs.getId()).toString();
            }
        };
    }

	public Converter<Long, WccAutokbs> getIdToWccAutokbsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccAutokbs>() {
            public com.nineclient.model.WccAutokbs convert(java.lang.Long id) {
                return WccAutokbs.findWccAutokbs(id);
            }
        };
    }

	public Converter<String, WccAutokbs> getStringToWccAutokbsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccAutokbs>() {
            public com.nineclient.model.WccAutokbs convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccAutokbs.class);
            }
        };
    }

	public Converter<WccAwardInfo, String> getWccAwardInfoToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccAwardInfo, java.lang.String>() {
            public String convert(WccAwardInfo wccAwardInfo) {
                return new StringBuilder().append(wccAwardInfo.getAwardName()).append(' ').append(wccAwardInfo.getAwardInfo()).append(' ').append(wccAwardInfo.getWinRate()).append(' ').append(wccAwardInfo.getCdkey()).toString();
            }
        };
    }

	public Converter<Long, WccAwardInfo> getIdToWccAwardInfoConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccAwardInfo>() {
            public com.nineclient.model.WccAwardInfo convert(java.lang.Long id) {
                return WccAwardInfo.findWccAwardInfo(id);
            }
        };
    }

	public Converter<String, WccAwardInfo> getStringToWccAwardInfoConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccAwardInfo>() {
            public com.nineclient.model.WccAwardInfo convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccAwardInfo.class);
            }
        };
    }

	public Converter<WccChatRecourds, String> getWccChatRecourdsToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccChatRecourds, java.lang.String>() {
            public String convert(WccChatRecourds wccChatRecourds) {
                return new StringBuilder().append(wccChatRecourds.getInsertTime()).append(' ').append(wccChatRecourds.getStartTime()).append(' ').append(wccChatRecourds.getEndTime()).toString();
            }
        };
    }

	public Converter<Long, WccChatRecourds> getIdToWccChatRecourdsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccChatRecourds>() {
            public com.nineclient.model.WccChatRecourds convert(java.lang.Long id) {
                return WccChatRecourds.findWccChatRecourds(id);
            }
        };
    }

	public Converter<String, WccChatRecourds> getStringToWccChatRecourdsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccChatRecourds>() {
            public com.nineclient.model.WccChatRecourds convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccChatRecourds.class);
            }
        };
    }

	public Converter<WccComment, String> getWccCommentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccComment, java.lang.String>() {
            public String convert(WccComment wccComment) {
                return new StringBuilder().append(wccComment.getContent()).toString();
            }
        };
    }

	public Converter<Long, WccComment> getIdToWccCommentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccComment>() {
            public com.nineclient.model.WccComment convert(java.lang.Long id) {
                return WccComment.findWccComment(id);
            }
        };
    }

	public Converter<String, WccComment> getStringToWccCommentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccComment>() {
            public com.nineclient.model.WccComment convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccComment.class);
            }
        };
    }

	public Converter<WccCommunity, String> getWccCommunityToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccCommunity, java.lang.String>() {
            public String convert(WccCommunity wccCommunity) {
                return new StringBuilder().append(wccCommunity.getCommunityName()).append(' ').append(wccCommunity.getCommunityUrl()).append(' ').append(wccCommunity.getTopicSort()).append(' ').append(wccCommunity.getHeadImage()).toString();
            }
        };
    }

	public Converter<Long, WccCommunity> getIdToWccCommunityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccCommunity>() {
            public com.nineclient.model.WccCommunity convert(java.lang.Long id) {
                return WccCommunity.findWccCommunity(id);
            }
        };
    }

	public Converter<String, WccCommunity> getStringToWccCommunityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccCommunity>() {
            public com.nineclient.model.WccCommunity convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccCommunity.class);
            }
        };
    }

	public Converter<WccContent, String> getWccContentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccContent, java.lang.String>() {
            public String convert(WccContent wccContent) {
                return new StringBuilder().append(wccContent.getTitle()).append(' ').append(wccContent.getUserName()).append(' ').append(wccContent.getContentUrl()).append(' ').append(wccContent.getContent()).toString();
            }
        };
    }

	public Converter<Long, WccContent> getIdToWccContentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccContent>() {
            public com.nineclient.model.WccContent convert(java.lang.Long id) {
                return WccContent.findWccContent(id);
            }
        };
    }

	public Converter<String, WccContent> getStringToWccContentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccContent>() {
            public com.nineclient.model.WccContent convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccContent.class);
            }
        };
    }

	public Converter<WccFriend, String> getWccFriendToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccFriend, java.lang.String>() {
            public String convert(WccFriend wccFriend) {
                return new StringBuilder().append(wccFriend.getNickName()).append(' ').append(wccFriend.getHeadImg()).append(' ').append(wccFriend.getArea()).append(' ').append(wccFriend.getSignature()).toString();
            }
        };
    }

	public Converter<Long, WccFriend> getIdToWccFriendConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccFriend>() {
            public com.nineclient.model.WccFriend convert(java.lang.Long id) {
                return WccFriend.findWccFriend(id);
            }
        };
    }

	public Converter<String, WccFriend> getStringToWccFriendConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccFriend>() {
            public com.nineclient.model.WccFriend convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccFriend.class);
            }
        };
    }

	public Converter<WccGroup, String> getWccGroupToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccGroup, java.lang.String>() {
            public String convert(WccGroup wccGroup) {
                return new StringBuilder().append(wccGroup.getName()).append(' ').append(wccGroup.getInsertTime()).toString();
            }
        };
    }

	public Converter<Long, WccGroup> getIdToWccGroupConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccGroup>() {
            public com.nineclient.model.WccGroup convert(java.lang.Long id) {
                return WccGroup.findWccGroup(id);
            }
        };
    }

	public Converter<String, WccGroup> getStringToWccGroupConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccGroup>() {
            public com.nineclient.model.WccGroup convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccGroup.class);
            }
        };
    }

	public Converter<WccGroupMessFriend, String> getWccGroupMessFriendToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccGroupMessFriend, java.lang.String>() {
            public String convert(WccGroupMessFriend wccGroupMessFriend) {
                return new StringBuilder().append(wccGroupMessFriend.getCreateTime()).toString();
            }
        };
    }

	public Converter<Long, WccGroupMessFriend> getIdToWccGroupMessFriendConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccGroupMessFriend>() {
            public com.nineclient.model.WccGroupMessFriend convert(java.lang.Long id) {
                return WccGroupMessFriend.findWccGroupMessFriend(id);
            }
        };
    }

	public Converter<String, WccGroupMessFriend> getStringToWccGroupMessFriendConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccGroupMessFriend>() {
            public com.nineclient.model.WccGroupMessFriend convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccGroupMessFriend.class);
            }
        };
    }

	public Converter<WccGroupMessage, String> getWccGroupMessageToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccGroupMessage, java.lang.String>() {
            public String convert(WccGroupMessage wccGroupMessage) {
                return new StringBuilder().append(wccGroupMessage.getCreateTime()).append(' ').append(wccGroupMessage.getTitle()).append(' ').append(wccGroupMessage.getAuthor()).append(' ').append(wccGroupMessage.getContent()).toString();
            }
        };
    }

	public Converter<Long, WccGroupMessage> getIdToWccGroupMessageConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccGroupMessage>() {
            public com.nineclient.model.WccGroupMessage convert(java.lang.Long id) {
                return WccGroupMessage.findWccGroupMessage(id);
            }
        };
    }

	public Converter<String, WccGroupMessage> getStringToWccGroupMessageConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccGroupMessage>() {
            public com.nineclient.model.WccGroupMessage convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccGroupMessage.class);
            }
        };
    }

	public Converter<WccInterActiveApp, String> getWccInterActiveAppToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccInterActiveApp, java.lang.String>() {
            public String convert(WccInterActiveApp wccInterActiveApp) {
                return new StringBuilder().append(wccInterActiveApp.getName()).append(' ').append(wccInterActiveApp.getKeyword()).append(' ').append(wccInterActiveApp.getUrl()).toString();
            }
        };
    }

	public Converter<Long, WccInterActiveApp> getIdToWccInterActiveAppConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccInterActiveApp>() {
            public com.nineclient.model.WccInterActiveApp convert(java.lang.Long id) {
                return WccInterActiveApp.findWccInterActiveApp(id);
            }
        };
    }

	public Converter<String, WccInterActiveApp> getStringToWccInterActiveAppConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccInterActiveApp>() {
            public com.nineclient.model.WccInterActiveApp convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccInterActiveApp.class);
            }
        };
    }

	public Converter<WccLeavemsgRecord, String> getWccLeavemsgRecordToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccLeavemsgRecord, java.lang.String>() {
            public String convert(WccLeavemsgRecord wccLeavemsgRecord) {
                return new StringBuilder().append(wccLeavemsgRecord.getMsgUserName()).append(' ').append(wccLeavemsgRecord.getMsgContent()).append(' ').append(wccLeavemsgRecord.getMsgTime()).toString();
            }
        };
    }

	public Converter<Long, WccLeavemsgRecord> getIdToWccLeavemsgRecordConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccLeavemsgRecord>() {
            public com.nineclient.model.WccLeavemsgRecord convert(java.lang.Long id) {
                return WccLeavemsgRecord.findWccLeavemsgRecord(id);
            }
        };
    }

	public Converter<String, WccLeavemsgRecord> getStringToWccLeavemsgRecordConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccLeavemsgRecord>() {
            public com.nineclient.model.WccLeavemsgRecord convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccLeavemsgRecord.class);
            }
        };
    }

	public Converter<WccLotteryActivity, String> getWccLotteryActivityToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccLotteryActivity, java.lang.String>() {
            public String convert(WccLotteryActivity wccLotteryActivity) {
                return new StringBuilder().append(wccLotteryActivity.getKeyword()).append(' ').append(wccLotteryActivity.getActivityName()).append(' ').append(wccLotteryActivity.getStartTime()).append(' ').append(wccLotteryActivity.getEndTime()).toString();
            }
        };
    }

	public Converter<Long, WccLotteryActivity> getIdToWccLotteryActivityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccLotteryActivity>() {
            public com.nineclient.model.WccLotteryActivity convert(java.lang.Long id) {
                return WccLotteryActivity.findWccLotteryActivity(id);
            }
        };
    }

	public Converter<String, WccLotteryActivity> getStringToWccLotteryActivityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccLotteryActivity>() {
            public com.nineclient.model.WccLotteryActivity convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccLotteryActivity.class);
            }
        };
    }

	public Converter<WccMaterials, String> getWccMaterialsToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccMaterials, java.lang.String>() {
            public String convert(WccMaterials wccMaterials) {
                return new StringBuilder().append(wccMaterials.getTitle()).append(' ').append(wccMaterials.getContent()).append(' ').append(wccMaterials.getResourceUrl()).append(' ').append(wccMaterials.getThumbnailUrl()).toString();
            }
        };
    }

	public Converter<Long, WccMaterials> getIdToWccMaterialsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccMaterials>() {
            public com.nineclient.model.WccMaterials convert(java.lang.Long id) {
                return WccMaterials.findWccMaterials(id);
            }
        };
    }

	public Converter<String, WccMaterials> getStringToWccMaterialsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccMaterials>() {
            public com.nineclient.model.WccMaterials convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccMaterials.class);
            }
        };
    }

	public Converter<WccMembershipLevel, String> getWccMembershipLevelToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccMembershipLevel, java.lang.String>() {
            public String convert(WccMembershipLevel wccMembershipLevel) {
                return new StringBuilder().append(wccMembershipLevel.getName()).append(' ').append(wccMembershipLevel.getCode()).append(' ').append(wccMembershipLevel.getCreateTime()).toString();
            }
        };
    }

	public Converter<Long, WccMembershipLevel> getIdToWccMembershipLevelConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccMembershipLevel>() {
            public com.nineclient.model.WccMembershipLevel convert(java.lang.Long id) {
                return WccMembershipLevel.findWccMembershipLevel(id);
            }
        };
    }

	public Converter<String, WccMembershipLevel> getStringToWccMembershipLevelConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccMembershipLevel>() {
            public com.nineclient.model.WccMembershipLevel convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccMembershipLevel.class);
            }
        };
    }

	public Converter<WccMessage, String> getWccMessageToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccMessage, java.lang.String>() {
            public String convert(WccMessage wccMessage) {
                return new StringBuilder().append(wccMessage.getContent()).append(' ').append(wccMessage.getDateTime()).append(' ').append(wccMessage.getMsg()).append(' ').append(wccMessage.getResourceUrl()).toString();
            }
        };
    }

	public Converter<Long, WccMessage> getIdToWccMessageConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccMessage>() {
            public com.nineclient.model.WccMessage convert(java.lang.Long id) {
                return WccMessage.findWccMessage(id);
            }
        };
    }

	public Converter<String, WccMessage> getStringToWccMessageConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccMessage>() {
            public com.nineclient.model.WccMessage convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccMessage.class);
            }
        };
    }

	public Converter<WccOffcAtivity, String> getWccOffcAtivityToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccOffcAtivity, java.lang.String>() {
            public String convert(WccOffcAtivity wccOffcAtivity) {
                return new StringBuilder().append(wccOffcAtivity.getName()).append(' ').append(wccOffcAtivity.getCreateTime()).append(' ').append(wccOffcAtivity.getStartTime()).append(' ').append(wccOffcAtivity.getEndTime()).toString();
            }
        };
    }

	public Converter<Long, WccOffcAtivity> getIdToWccOffcAtivityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccOffcAtivity>() {
            public com.nineclient.model.WccOffcAtivity convert(java.lang.Long id) {
                return WccOffcAtivity.findWccOffcAtivity(id);
            }
        };
    }

	public Converter<String, WccOffcAtivity> getStringToWccOffcAtivityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccOffcAtivity>() {
            public com.nineclient.model.WccOffcAtivity convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccOffcAtivity.class);
            }
        };
    }

	public Converter<WccOption, String> getWccOptionToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccOption, java.lang.String>() {
            public String convert(WccOption wccOption) {
                return new StringBuilder().append(wccOption.getQuestionNo()).append(' ').append(wccOption.getSort()).toString();
            }
        };
    }

	public Converter<Long, WccOption> getIdToWccOptionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccOption>() {
            public com.nineclient.model.WccOption convert(java.lang.Long id) {
                return WccOption.findWccOption(id);
            }
        };
    }

	public Converter<String, WccOption> getStringToWccOptionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccOption>() {
            public com.nineclient.model.WccOption convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccOption.class);
            }
        };
    }


	public Converter<WccPlate, String> getWccPlateToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccPlate, java.lang.String>() {
            public String convert(WccPlate wccPlate) {
                return new StringBuilder().append(wccPlate.getPlateName()).append(' ').append(wccPlate.getRemark()).toString();
            }
        };
    }

	public Converter<Long, WccPlate> getIdToWccPlateConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccPlate>() {
            public com.nineclient.model.WccPlate convert(java.lang.Long id) {
                return WccPlate.findWccPlate(id);
            }
        };
    }

	public Converter<String, WccPlate> getStringToWccPlateConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccPlate>() {
            public com.nineclient.model.WccPlate convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccPlate.class);
            }
        };
    }

	public Converter<WccPlatformUser, String> getWccPlatformUserToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccPlatformUser, java.lang.String>() {
            public String convert(WccPlatformUser wccPlatformUser) {
                return new StringBuilder().append(wccPlatformUser.getAccount()).toString();
            }
        };
    }

	public Converter<Long, WccPlatformUser> getIdToWccPlatformUserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccPlatformUser>() {
            public com.nineclient.model.WccPlatformUser convert(java.lang.Long id) {
                return WccPlatformUser.findWccPlatformUser(id);
            }
        };
    }

	public Converter<String, WccPlatformUser> getStringToWccPlatformUserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccPlatformUser>() {
            public com.nineclient.model.WccPlatformUser convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccPlatformUser.class);
            }
        };
    }

/*	public Converter<WccPraise, String> getWccPraiseToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccPraise, java.lang.String>() {
            public String convert(WccPraise wccPraise) {
                return new StringBuilder().append(wccPraise.getId()).toString();
            }
        };
    }*/

	public Converter<Long, WccPraise> getIdToWccPraiseConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccPraise>() {
            public com.nineclient.model.WccPraise convert(java.lang.Long id) {
                return WccPraise.findWccPraise(id);
            }
        };
    }

	public Converter<String, WccPraise> getStringToWccPraiseConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccPraise>() {
            public com.nineclient.model.WccPraise convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccPraise.class);
            }
        };
    }

	public Converter<WccQrcode, String> getWccQrcodeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccQrcode, java.lang.String>() {
            public String convert(WccQrcode wccQrcode) {
                return new StringBuilder().append(wccQrcode.getCodeUrl()).append(' ').append(wccQrcode.getCodeId()).append(' ').append(wccQrcode.getExpireSeconds()).append(' ').append(wccQrcode.getUseType()).toString();
            }
        };
    }

	public Converter<Long, WccQrcode> getIdToWccQrcodeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccQrcode>() {
            public com.nineclient.model.WccQrcode convert(java.lang.Long id) {
                return WccQrcode.findWccQrcode(id);
            }
        };
    }

	public Converter<String, WccQrcode> getStringToWccQrcodeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccQrcode>() {
            public com.nineclient.model.WccQrcode convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccQrcode.class);
            }
        };
    }

	public Converter<WccQuestion, String> getWccQuestionToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccQuestion, java.lang.String>() {
            public String convert(WccQuestion wccQuestion) {
                return new StringBuilder().append(wccQuestion.getName()).append(' ').append(wccQuestion.getQuestionNo()).append(' ').append(wccQuestion.getSort()).toString();
            }
        };
    }

	public Converter<Long, WccQuestion> getIdToWccQuestionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccQuestion>() {
            public com.nineclient.model.WccQuestion convert(java.lang.Long id) {
                return WccQuestion.findWccQuestion(id);
            }
        };
    }

	public Converter<String, WccQuestion> getStringToWccQuestionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccQuestion>() {
            public com.nineclient.model.WccQuestion convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccQuestion.class);
            }
        };
    }

	public Converter<WccQuestionnaire, String> getWccQuestionnaireToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccQuestionnaire, java.lang.String>() {
            public String convert(WccQuestionnaire wccQuestionnaire) {
                return new StringBuilder().append(wccQuestionnaire.getName()).append(' ').append(wccQuestionnaire.getQuestionnaireCode()).append(' ').append(wccQuestionnaire.getVisableTime()).append(' ').append(wccQuestionnaire.getRemark()).toString();
            }
        };
    }

	public Converter<Long, WccQuestionnaire> getIdToWccQuestionnaireConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccQuestionnaire>() {
            public com.nineclient.model.WccQuestionnaire convert(java.lang.Long id) {
                return WccQuestionnaire.findWccQuestionnaire(id);
            }
        };
    }

	public Converter<String, WccQuestionnaire> getStringToWccQuestionnaireConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccQuestionnaire>() {
            public com.nineclient.model.WccQuestionnaire convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccQuestionnaire.class);
            }
        };
    }

	public Converter<WccSncode, String> getWccSncodeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccSncode, java.lang.String>() {
            public String convert(WccSncode wccSncode) {
                return new StringBuilder().append(wccSncode.getSnCode()).append(' ').append(wccSncode.getAwardTime()).append(' ').append(wccSncode.getSnRemark()).toString();
            }
        };
    }

	public Converter<Long, WccSncode> getIdToWccSncodeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccSncode>() {
            public com.nineclient.model.WccSncode convert(java.lang.Long id) {
                return WccSncode.findWccSncode(id);
            }
        };
    }

	public Converter<String, WccSncode> getStringToWccSncodeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccSncode>() {
            public com.nineclient.model.WccSncode convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccSncode.class);
            }
        };
    }

	public Converter<WccStore, String> getWccStoreToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccStore, java.lang.String>() {
            public String convert(WccStore wccStore) {
                return new StringBuilder().append(wccStore.getStoreName()).append(' ').append(wccStore.getStoreAddres()).append(' ').append(wccStore.getStorePhone()).append(' ').append(wccStore.getStoreText()).toString();
            }
        };
    }

	public Converter<Long, WccStore> getIdToWccStoreConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccStore>() {
            public com.nineclient.model.WccStore convert(java.lang.Long id) {
                return WccStore.findWccStore(id);
            }
        };
    }

	public Converter<String, WccStore> getStringToWccStoreConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccStore>() {
            public com.nineclient.model.WccStore convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccStore.class);
            }
        };
    }

	public Converter<WccTopic, String> getWccTopicToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccTopic, java.lang.String>() {
            public String convert(WccTopic wccTopic) {
                return new StringBuilder().append(wccTopic.getContent()).append(' ').append(wccTopic.getCommentCount()).append(' ').append(wccTopic.getPraiseCount()).append(' ').append(wccTopic.getTopicImage()).toString();
            }
        };
    }

	public Converter<Long, WccTopic> getIdToWccTopicConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccTopic>() {
            public com.nineclient.model.WccTopic convert(java.lang.Long id) {
                return WccTopic.findWccTopic(id);
            }
        };
    }

	public Converter<String, WccTopic> getStringToWccTopicConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccTopic>() {
            public com.nineclient.model.WccTopic convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccTopic.class);
            }
        };
    }

	public Converter<WccUser, String> getWccUserToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccUser, java.lang.String>() {
            public String convert(WccUser wccUser) {
                return new StringBuilder().append(wccUser.getUserName()).append(' ').append(wccUser.getCommentCount()).append(' ').append(wccUser.getPraiseCount()).append(' ').append(wccUser.getGivenCommentCount()).toString();
            }
        };
    }

	public Converter<Long, WccUser> getIdToWccUserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccUser>() {
            public com.nineclient.model.WccUser convert(java.lang.Long id) {
                return WccUser.findWccUser(id);
            }
        };
    }

	public Converter<String, WccUser> getStringToWccUserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccUser>() {
            public com.nineclient.model.WccUser convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccUser.class);
            }
        };
    }

	public Converter<WccUserFieldValue, String> getWccUserFieldValueToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccUserFieldValue, java.lang.String>() {
            public String convert(WccUserFieldValue wccUserFieldValue) {
                return new StringBuilder().append(wccUserFieldValue.getCreateTime()).append(' ').append(wccUserFieldValue.getRemark()).append(' ').append(wccUserFieldValue.getUserFieldValue()).toString();
            }
        };
    }

	public Converter<Long, WccUserFieldValue> getIdToWccUserFieldValueConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccUserFieldValue>() {
            public com.nineclient.model.WccUserFieldValue convert(java.lang.Long id) {
                return WccUserFieldValue.findWccUserFieldValue(id);
            }
        };
    }

	public Converter<String, WccUserFieldValue> getStringToWccUserFieldValueConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccUserFieldValue>() {
            public com.nineclient.model.WccUserFieldValue convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccUserFieldValue.class);
            }
        };
    }

	public Converter<WccUserFields, String> getWccUserFieldsToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccUserFields, java.lang.String>() {
            public String convert(WccUserFields wccUserFields) {
                return new StringBuilder().append(wccUserFields.getCreateTime()).append(' ').append(wccUserFields.getFieldNameCh()).append(' ').append(wccUserFields.getFieldNameEN()).append(' ').append(wccUserFields.getRemark()).toString();
            }
        };
    }

	public Converter<Long, WccUserFields> getIdToWccUserFieldsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccUserFields>() {
            public com.nineclient.model.WccUserFields convert(java.lang.Long id) {
                return WccUserFields.findWccUserFields(id);
            }
        };
    }

	public Converter<String, WccUserFields> getStringToWccUserFieldsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccUserFields>() {
            public com.nineclient.model.WccUserFields convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccUserFields.class);
            }
        };
    }

	public Converter<WccUserLottery, String> getWccUserLotteryToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccUserLottery, java.lang.String>() {
            public String convert(WccUserLottery wccUserLottery) {
                return new StringBuilder().append(wccUserLottery.getLotteryNumber()).toString();
            }
        };
    }

	public Converter<Long, WccUserLottery> getIdToWccUserLotteryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccUserLottery>() {
            public com.nineclient.model.WccUserLottery convert(java.lang.Long id) {
                return WccUserLottery.findWccUserLottery(id);
            }
        };
    }

	public Converter<String, WccUserLottery> getStringToWccUserLotteryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccUserLottery>() {
            public com.nineclient.model.WccUserLottery convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccUserLottery.class);
            }
        };
    }

	public Converter<WccUserName, String> getWccUserNameToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccUserName, java.lang.String>() {
            public String convert(WccUserName wccUserName) {
                return new StringBuilder().append(wccUserName.getCreateTime()).append(' ').append(wccUserName.getTabName()).append(' ').append(wccUserName.getRemark()).toString();
            }
        };
    }

	public Converter<Long, WccUserName> getIdToWccUserNameConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccUserName>() {
            public com.nineclient.model.WccUserName convert(java.lang.Long id) {
                return WccUserName.findWccUserName(id);
            }
        };
    }

	public Converter<String, WccUserName> getStringToWccUserNameConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccUserName>() {
            public com.nineclient.model.WccUserName convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccUserName.class);
            }
        };
    }

	public Converter<WccUserPagetemp, String> getWccUserPagetempToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccUserPagetemp, java.lang.String>() {
            public String convert(WccUserPagetemp wccUserPagetemp) {
                return new StringBuilder().append(wccUserPagetemp.getPageTitle()).append(' ').append(wccUserPagetemp.getProName()).append(' ').append(wccUserPagetemp.getTemImage()).append(' ').append(wccUserPagetemp.getRemark()).toString();
            }
        };
    }

	public Converter<Long, WccUserPagetemp> getIdToWccUserPagetempConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccUserPagetemp>() {
            public com.nineclient.model.WccUserPagetemp convert(java.lang.Long id) {
                return WccUserPagetemp.findWccUserPagetemp(id);
            }
        };
    }

	public Converter<String, WccUserPagetemp> getStringToWccUserPagetempConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccUserPagetemp>() {
            public com.nineclient.model.WccUserPagetemp convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccUserPagetemp.class);
            }
        };
    }

	public Converter<WccUtility, String> getWccUtilityToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccUtility, java.lang.String>() {
            public String convert(WccUtility wccUtility) {
                return new StringBuilder().append(wccUtility.getName()).append(' ').append(wccUtility.getRemark()).append(' ').append(wccUtility.getUrl()).toString();
            }
        };
    }

	public Converter<Long, WccUtility> getIdToWccUtilityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccUtility>() {
            public com.nineclient.model.WccUtility convert(java.lang.Long id) {
                return WccUtility.findWccUtility(id);
            }
        };
    }

	public Converter<String, WccUtility> getStringToWccUtilityConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccUtility>() {
            public com.nineclient.model.WccUtility convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccUtility.class);
            }
        };
    }

	public Converter<WccWelcomkbs, String> getWccWelcomkbsToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccWelcomkbs, java.lang.String>() {
            public String convert(WccWelcomkbs wccWelcomkbs) {
                return new StringBuilder().append(wccWelcomkbs.getTitle()).append(' ').append(wccWelcomkbs.getContent()).append(' ').append(wccWelcomkbs.getInsertTime()).toString();
            }
        };
    }

	public Converter<Long, WccWelcomkbs> getIdToWccWelcomkbsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccWelcomkbs>() {
            public com.nineclient.model.WccWelcomkbs convert(java.lang.Long id) {
                return WccWelcomkbs.findWccWelcomkbs(id);
            }
        };
    }

	public Converter<String, WccWelcomkbs> getStringToWccWelcomkbsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccWelcomkbs>() {
            public com.nineclient.model.WccWelcomkbs convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WccWelcomkbs.class);
            }
        };
    }

	public Converter<PubOrganization, String> getPubOrganizationToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.PubOrganization, java.lang.String>() {
            public String convert(PubOrganization pubOrganization) {
                return new StringBuilder().append(pubOrganization.getParentId()).append(' ').append(pubOrganization.getName()).append(' ').append(pubOrganization.getSort()).append(' ').append(pubOrganization.getRemark()).toString();
            }
        };
    }

	public Converter<Long, PubOrganization> getIdToPubOrganizationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.PubOrganization>() {
            public com.nineclient.model.PubOrganization convert(java.lang.Long id) {
                return PubOrganization.findPubOrganization(id);
            }
        };
    }

	public Converter<String, PubOrganization> getStringToPubOrganizationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.PubOrganization>() {
            public com.nineclient.model.PubOrganization convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), PubOrganization.class);
            }
        };
    }
	//VOC

	public Converter<VocAccount, String> getVocAccountToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.VocAccount, java.lang.String>() {
            public String convert(VocAccount vocAccount) {
                return new StringBuilder().append(vocAccount.getAccount()).append(' ').append(vocAccount.getCookie()).append(' ').append(vocAccount.getPassword()).append(' ').append(vocAccount.getSort()).toString();
            }
        };
    }

	public Converter<Long, VocAccount> getIdToVocAccountConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.VocAccount>() {
            public com.nineclient.model.VocAccount convert(java.lang.Long id) {
                return VocAccount.findVocAccount(id);
            }
        };
    }

	public Converter<String, VocAccount> getStringToVocAccountConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.VocAccount>() {
            public com.nineclient.model.VocAccount convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), VocAccount.class);
            }
        };
    }

	public Converter<VocAppkey, String> getVocAppkeyToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.VocAppkey, java.lang.String>() {
            public String convert(VocAppkey vocAppkey) {
                return new StringBuilder().append(vocAppkey.getAppkey()).append(' ').append(vocAppkey.getClientSecret()).append(' ').append(vocAppkey.getName()).append(' ').append(vocAppkey.getCreateTime()).toString();
            }
        };
    }

	public Converter<Long, VocAppkey> getIdToVocAppkeyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.VocAppkey>() {
            public com.nineclient.model.VocAppkey convert(java.lang.Long id) {
                return VocAppkey.findVocAppkey(id);
            }
        };
    }

	public Converter<String, VocAppkey> getStringToVocAppkeyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.VocAppkey>() {
            public com.nineclient.model.VocAppkey convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), VocAppkey.class);
            }
        };
    }

	public Converter<VocBrand, String> getVocBrandToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.VocBrand, java.lang.String>() {
            public String convert(VocBrand vocBrand) {
                return new StringBuilder().append(vocBrand.getBrandName()).append(' ').append(vocBrand.getRemark()).append(' ').append(vocBrand.getCreateTime()).append(' ').append(vocBrand.getSort()).toString();
            }
        };
    }

	public Converter<Long, VocBrand> getIdToVocBrandConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.VocBrand>() {
            public com.nineclient.model.VocBrand convert(java.lang.Long id) {
                return VocBrand.findVocBrand(id);
            }
        };
    }

	public Converter<String, VocBrand> getStringToVocBrandConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.VocBrand>() {
            public com.nineclient.model.VocBrand convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), VocBrand.class);
            }
        };
    }

	public Converter<VocComment, String> getVocCommentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.VocComment, java.lang.String>() {
            public String convert(VocComment vocComment) {
                return new StringBuilder().append(vocComment.getCommentContent()).append(' ').append(vocComment.getCommentTitle()).append(' ').append(vocComment.getCommentTime()).append(' ').append(vocComment.getBuyTime()).toString();
            }
        };
    }

	public Converter<Long, VocComment> getIdToVocCommentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.VocComment>() {
            public com.nineclient.model.VocComment convert(java.lang.Long id) {
                return VocComment.findVocComment(id);
            }
        };
    }

	public Converter<String, VocComment> getStringToVocCommentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.VocComment>() {
            public com.nineclient.model.VocComment convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), VocComment.class);
            }
        };
    }

	public Converter<VocCommentCategory, String> getVocCommentCategoryToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.VocCommentCategory, java.lang.String>() {
            public String convert(VocCommentCategory vocCommentCategory) {
                return new StringBuilder().append(vocCommentCategory.getName()).append(' ').append(vocCommentCategory.getSort()).append(' ').append(vocCommentCategory.getCreateTime()).append(' ').append(vocCommentCategory.getRemark()).toString();
            }
        };
    }

	public Converter<Long, VocCommentCategory> getIdToVocCommentCategoryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.VocCommentCategory>() {
            public com.nineclient.model.VocCommentCategory convert(java.lang.Long id) {
                return VocCommentCategory.findVocCommentCategory(id);
            }
        };
    }

	public Converter<String, VocCommentCategory> getStringToVocCommentCategoryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.VocCommentCategory>() {
            public com.nineclient.model.VocCommentCategory convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), VocCommentCategory.class);
            }
        };
    }

	public Converter<VocCommentLevelCategory, String> getVocCommentLevelCategoryToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.VocCommentLevelCategory, java.lang.String>() {
            public String convert(VocCommentLevelCategory vocCommentLevelCategory) {
                return new StringBuilder().append(vocCommentLevelCategory.getName()).append(' ').append(vocCommentLevelCategory.getParentId()).append(' ').append(vocCommentLevelCategory.getCreateTime()).toString();
            }
        };
    }

	public Converter<Long, VocCommentLevelCategory> getIdToVocCommentLevelCategoryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.VocCommentLevelCategory>() {
            public com.nineclient.model.VocCommentLevelCategory convert(java.lang.Long id) {
                return VocCommentLevelCategory.findVocCommentLevelCategory(id);
            }
        };
    }

	public Converter<String, VocCommentLevelCategory> getStringToVocCommentLevelCategoryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.VocCommentLevelCategory>() {
            public com.nineclient.model.VocCommentLevelCategory convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), VocCommentLevelCategory.class);
            }
        };
    }

	public Converter<VocCommentLevelRule, String> getVocCommentLevelRuleToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.VocCommentLevelRule, java.lang.String>() {
            public String convert(VocCommentLevelRule vocCommentLevelRule) {
                return new StringBuilder().append(vocCommentLevelRule.getName()).append(' ').append(vocCommentLevelRule.getCreateTime()).toString();
            }
        };
    }

	public Converter<Long, VocCommentLevelRule> getIdToVocCommentLevelRuleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.VocCommentLevelRule>() {
            public com.nineclient.model.VocCommentLevelRule convert(java.lang.Long id) {
                return VocCommentLevelRule.findVocCommentLevelRule(id);
            }
        };
    }

	public Converter<String, VocCommentLevelRule> getStringToVocCommentLevelRuleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.VocCommentLevelRule>() {
            public com.nineclient.model.VocCommentLevelRule convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), VocCommentLevelRule.class);
            }
        };
    }

	public Converter<VocEmail, String> getVocEmailToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.VocEmail, java.lang.String>() {
            public String convert(VocEmail vocEmail) {
                return new StringBuilder().append(vocEmail.getName()).append(' ').append(vocEmail.getEmail()).append(' ').append(vocEmail.getRemark()).append(' ').append(vocEmail.getCreateTime()).toString();
            }
        };
    }

	public Converter<Long, VocEmail> getIdToVocEmailConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.VocEmail>() {
            public com.nineclient.model.VocEmail convert(java.lang.Long id) {
                return VocEmail.findVocEmail(id);
            }
        };
    }

	public Converter<String, VocEmail> getStringToVocEmailConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.VocEmail>() {
            public com.nineclient.model.VocEmail convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), VocEmail.class);
            }
        };
    }
	 public Converter<UmpParentBusinessType, String> getUmpParentBusinessTypeToStringConverter() {
	        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.UmpParentBusinessType, java.lang.String>() {
	            public String convert(UmpParentBusinessType umpParentBusinessType) {
	                return new StringBuilder().append(umpParentBusinessType.getBusinessName()).toString();
	            }
	        };
	    }

		public Converter<Long, UmpParentBusinessType> getIdToUmpParentBusinessTypeConverter() {
	        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.UmpParentBusinessType>() {
	            public com.nineclient.model.UmpParentBusinessType convert(java.lang.Long id) {
	                return UmpParentBusinessType.findUmpParentBusinessType(id);
	            }
	        };
	    }

		public Converter<String, UmpParentBusinessType> getStringToUmpParentBusinessTypeConverter() {
	        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.UmpParentBusinessType>() {
	            public com.nineclient.model.UmpParentBusinessType convert(String id) {
	                return getObject().convert(getObject().convert(id, Long.class), UmpParentBusinessType.class);
	            }
	        };
	    }
		public Converter<VocShop, String> getVocShopToStringConverter() {
	        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.VocShop, java.lang.String>() {
	            public String convert(VocShop vocShop) {
	                return new StringBuilder().append(vocShop.getName()).toString();
	            }
	        };
	    }
		public Converter<Long, VocShop> getIdToVocShopConverter() {
	        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.VocShop>() {
	            public com.nineclient.model.VocShop convert(java.lang.Long id) {
	                return VocShop.findVocShop(id);
	            }
	        };
	    }
		public Converter<String, VocShop> getStringToVocShopConverter() {
	        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.VocShop>() {
	            public com.nineclient.model.VocShop convert(String id) {
	                return getObject().convert(getObject().convert(id, Long.class), VocShop.class);
	            }
	        };
	    }

//	public Converter<WccMenu, String> getWccMenuToStringConverter() {
//        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.WccMenu, java.lang.String>() {
//            public String convert(WccMenu wccMenu) {
//                return new StringBuilder().append(wccMenu.getMenuName()).append(' ').append(wccMenu.getMkey()).append(' ').append(wccMenu.getType()).append(' ').append(wccMenu.getParentId()).toString();
//            }
//        };
//    }
//
//	public Converter<Long, WccMenu> getIdToWccMenuConverter() {
//        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.WccMenu>() {
//            public com.nineclient.model.WccMenu convert(java.lang.Long id) {
//                return WccMenu.findWccMenu(id);
//            }
//        };
//    }
//
//	public Converter<String, WccMenu> getStringToWccMenuConverter() {
//        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.WccMenu>() {
//            public com.nineclient.model.WccMenu convert(String id) {
//                return getObject().convert(getObject().convert(id, Long.class), WccMenu.class);
//            }
//        };
//    }
	
	public Converter<PubOperator, String> getPubOperatorToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.nineclient.model.PubOperator, java.lang.String>() {
            public String convert(PubOperator pubOperator) {
                return new StringBuilder().append(pubOperator.getEmail()).append(' ').append(pubOperator.getOperatorName()).append(' ').append(pubOperator.getAccount()).append(' ').append(pubOperator.getPassword()).toString();
            }
        };
    }

	public Converter<Long, PubOperator> getIdToPubOperatorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.nineclient.model.PubOperator>() {
            public com.nineclient.model.PubOperator convert(java.lang.Long id) {
                return PubOperator.findPubOperator(id);
            }
        };
    }

	public Converter<String, PubOperator> getStringToPubOperatorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.nineclient.model.PubOperator>() {
            public com.nineclient.model.PubOperator convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), PubOperator.class);
            }
        };
    }
	
	public void installLabelConverters(FormatterRegistry registry) {
		registry.addConverter(getPubOrganizationToStringConverter());
	    registry.addConverter(getIdToPubOrganizationConverter());
	    registry.addConverter(getStringToPubOrganizationConverter());
        registry.addConverter(getUmpAuthorityToStringConverter());
        registry.addConverter(getIdToUmpAuthorityConverter());
        registry.addConverter(getStringToUmpAuthorityConverter());
        registry.addConverter(getUmpBrandToStringConverter());
        registry.addConverter(getIdToUmpBrandConverter());
        registry.addConverter(getStringToUmpBrandConverter());
        registry.addConverter(getUmpBusinessTypeToStringConverter());
        registry.addConverter(getIdToUmpBusinessTypeConverter());
        registry.addConverter(getStringToUmpBusinessTypeConverter());
        registry.addConverter(getUmpChannelToStringConverter());
        registry.addConverter(getIdToUmpChannelConverter());
        registry.addConverter(getStringToUmpChannelConverter());
        registry.addConverter(getUmpCompanyToStringConverter());
        registry.addConverter(getIdToUmpCompanyConverter());
        registry.addConverter(getStringToUmpCompanyConverter());
        registry.addConverter(getUmpDictionaryToStringConverter());
        registry.addConverter(getIdToUmpDictionaryConverter());
        registry.addConverter(getStringToUmpDictionaryConverter());
        registry.addConverter(getUmpOperatorToStringConverter());
        registry.addConverter(getIdToUmpOperatorConverter());
        registry.addConverter(getStringToUmpOperatorConverter());
        registry.addConverter(getUmpConfigToStringConverter());
        registry.addConverter(getIdToUmpConfigConverter());
        registry.addConverter(getStringToUmpConfigConverter());
//        registry.addConverter(getUmpProductToStringConverter());
//        registry.addConverter(getIdToUmpProductConverter());
//        registry.addConverter(getStringToUmpProductConverter());
        registry.addConverter(getUmpRoleToStringConverter());
        registry.addConverter(getIdToUmpRoleConverter());
        registry.addConverter(getStringToUmpRoleConverter());
        registry.addConverter(getUmpVersionToStringConverter());
        registry.addConverter(getIdToUmpVersionConverter());
        registry.addConverter(getStringToUmpVersionConverter());
        registry.addConverter(getWccActivitiesToStringConverter());
        registry.addConverter(getIdToWccActivitiesConverter());
        registry.addConverter(getStringToWccActivitiesConverter());
        registry.addConverter(getWccAnswerToStringConverter());
        registry.addConverter(getIdToWccAnswerConverter());
        registry.addConverter(getStringToWccAnswerConverter());
        registry.addConverter(getWccAutokbsToStringConverter());
        registry.addConverter(getIdToWccAutokbsConverter());
        registry.addConverter(getStringToWccAutokbsConverter());
        registry.addConverter(getWccAwardInfoToStringConverter());
        registry.addConverter(getIdToWccAwardInfoConverter());
        registry.addConverter(getStringToWccAwardInfoConverter());
        registry.addConverter(getWccChatRecourdsToStringConverter());
        registry.addConverter(getIdToWccChatRecourdsConverter());
        registry.addConverter(getStringToWccChatRecourdsConverter());
        registry.addConverter(getWccCommentToStringConverter());
        registry.addConverter(getIdToWccCommentConverter());
        registry.addConverter(getStringToWccCommentConverter());
        registry.addConverter(getWccCommunityToStringConverter());
        registry.addConverter(getIdToWccCommunityConverter());
        registry.addConverter(getStringToWccCommunityConverter());
        registry.addConverter(getWccContentToStringConverter());
        registry.addConverter(getIdToWccContentConverter());
        registry.addConverter(getStringToWccContentConverter());
        registry.addConverter(getWccFriendToStringConverter());
        registry.addConverter(getIdToWccFriendConverter());
        registry.addConverter(getStringToWccFriendConverter());
        registry.addConverter(getWccGroupToStringConverter());
        registry.addConverter(getIdToWccGroupConverter());
        registry.addConverter(getStringToWccGroupConverter());
        registry.addConverter(getWccGroupMessFriendToStringConverter());
        registry.addConverter(getIdToWccGroupMessFriendConverter());
        registry.addConverter(getStringToWccGroupMessFriendConverter());
        registry.addConverter(getWccGroupMessageToStringConverter());
        registry.addConverter(getIdToWccGroupMessageConverter());
        registry.addConverter(getStringToWccGroupMessageConverter());
        registry.addConverter(getWccInterActiveAppToStringConverter());
        registry.addConverter(getIdToWccInterActiveAppConverter());
        registry.addConverter(getStringToWccInterActiveAppConverter());
        registry.addConverter(getWccLeavemsgRecordToStringConverter());
        registry.addConverter(getIdToWccLeavemsgRecordConverter());
        registry.addConverter(getStringToWccLeavemsgRecordConverter());
        registry.addConverter(getWccLotteryActivityToStringConverter());
        registry.addConverter(getIdToWccLotteryActivityConverter());
        registry.addConverter(getStringToWccLotteryActivityConverter());
        registry.addConverter(getWccMaterialsToStringConverter());
        registry.addConverter(getIdToWccMaterialsConverter());
        registry.addConverter(getStringToWccMaterialsConverter());
        registry.addConverter(getWccMembershipLevelToStringConverter());
        registry.addConverter(getIdToWccMembershipLevelConverter());
        registry.addConverter(getStringToWccMembershipLevelConverter());
        registry.addConverter(getWccMessageToStringConverter());
        registry.addConverter(getIdToWccMessageConverter());
        registry.addConverter(getStringToWccMessageConverter());
        registry.addConverter(getWccOffcAtivityToStringConverter());
        registry.addConverter(getIdToWccOffcAtivityConverter());
        registry.addConverter(getStringToWccOffcAtivityConverter());
        registry.addConverter(getWccOptionToStringConverter());
        registry.addConverter(getIdToWccOptionConverter());
        registry.addConverter(getStringToWccOptionConverter());
        registry.addConverter(getWccPlateToStringConverter());
        registry.addConverter(getIdToWccPlateConverter());
        registry.addConverter(getStringToWccPlateConverter());
        registry.addConverter(getWccPlatformUserToStringConverter());
        registry.addConverter(getIdToWccPlatformUserConverter());
        registry.addConverter(getStringToWccPlatformUserConverter());
        //registry.addConverter(getWccPraiseToStringConverter());
        registry.addConverter(getIdToWccPraiseConverter());
        registry.addConverter(getStringToWccPraiseConverter());
        registry.addConverter(getWccQrcodeToStringConverter());
        registry.addConverter(getIdToWccQrcodeConverter());
        registry.addConverter(getStringToWccQrcodeConverter());
        registry.addConverter(getWccQuestionToStringConverter());
        registry.addConverter(getIdToWccQuestionConverter());
        registry.addConverter(getStringToWccQuestionConverter());
        registry.addConverter(getWccQuestionnaireToStringConverter());
        registry.addConverter(getIdToWccQuestionnaireConverter());
        registry.addConverter(getStringToWccQuestionnaireConverter());
        registry.addConverter(getWccSncodeToStringConverter());
        registry.addConverter(getIdToWccSncodeConverter());
        registry.addConverter(getStringToWccSncodeConverter());
        registry.addConverter(getWccStoreToStringConverter());
        registry.addConverter(getIdToWccStoreConverter());
        registry.addConverter(getStringToWccStoreConverter());
        registry.addConverter(getWccTopicToStringConverter());
        registry.addConverter(getIdToWccTopicConverter());
        registry.addConverter(getStringToWccTopicConverter());
        registry.addConverter(getWccUserToStringConverter());
        registry.addConverter(getIdToWccUserConverter());
        registry.addConverter(getStringToWccUserConverter());
        registry.addConverter(getWccUserFieldValueToStringConverter());
        registry.addConverter(getIdToWccUserFieldValueConverter());
        registry.addConverter(getStringToWccUserFieldValueConverter());
        registry.addConverter(getWccUserFieldsToStringConverter());
        registry.addConverter(getIdToWccUserFieldsConverter());
        registry.addConverter(getStringToWccUserFieldsConverter());
        registry.addConverter(getWccUserLotteryToStringConverter());
        registry.addConverter(getIdToWccUserLotteryConverter());
        registry.addConverter(getStringToWccUserLotteryConverter());
        registry.addConverter(getWccUserNameToStringConverter());
        registry.addConverter(getIdToWccUserNameConverter());
        registry.addConverter(getStringToWccUserNameConverter());
        registry.addConverter(getWccUserPagetempToStringConverter());
        registry.addConverter(getIdToWccUserPagetempConverter());
        registry.addConverter(getStringToWccUserPagetempConverter());
        registry.addConverter(getWccUtilityToStringConverter());
        registry.addConverter(getIdToWccUtilityConverter());
        registry.addConverter(getStringToWccUtilityConverter());
        registry.addConverter(getWccWelcomkbsToStringConverter());
        registry.addConverter(getIdToWccWelcomkbsConverter());
        registry.addConverter(getStringToWccWelcomkbsConverter());
//        registry.addConverter(getWccMenuToStringConverter());
//        registry.addConverter(getIdToWccMenuConverter());
//        registry.addConverter(getStringToWccMenuConverter());
        registry.addConverter(getPubOperatorToStringConverter());
        registry.addConverter(getIdToPubOperatorConverter());
        registry.addConverter(getStringToPubOperatorConverter());
        //VOC
        registry.addConverter(getVocAccountToStringConverter());
        registry.addConverter(getIdToVocAccountConverter());
        registry.addConverter(getStringToVocAccountConverter());
        registry.addConverter(getVocAppkeyToStringConverter());
        registry.addConverter(getIdToVocAppkeyConverter());
        registry.addConverter(getStringToVocAppkeyConverter());
        registry.addConverter(getVocBrandToStringConverter());
        registry.addConverter(getIdToVocBrandConverter());
        registry.addConverter(getStringToVocBrandConverter());
        registry.addConverter(getVocCommentToStringConverter());
        registry.addConverter(getIdToVocCommentConverter());
        registry.addConverter(getStringToVocCommentConverter());
        registry.addConverter(getVocCommentCategoryToStringConverter());
        registry.addConverter(getIdToVocCommentCategoryConverter());
        registry.addConverter(getStringToVocCommentCategoryConverter());
        registry.addConverter(getVocCommentLevelCategoryToStringConverter());
        registry.addConverter(getIdToVocCommentLevelCategoryConverter());
        registry.addConverter(getStringToVocCommentLevelCategoryConverter());
        registry.addConverter(getVocCommentLevelRuleToStringConverter());
        registry.addConverter(getIdToVocCommentLevelRuleConverter());
        registry.addConverter(getStringToVocCommentLevelRuleConverter());
        registry.addConverter(getVocEmailToStringConverter());
        registry.addConverter(getIdToVocEmailConverter());
        registry.addConverter(getStringToVocEmailConverter());
//        registry.addConverter(getStringToUmpProductConverter());
//        registry.addConverter(getIdToUmpProductConverter());
//        registry.addConverter(getUmpProductToStringConverter());
        registry.addConverter(getUmpParentBusinessTypeToStringConverter());
        registry.addConverter(getIdToUmpParentBusinessTypeConverter());
        registry.addConverter(getStringToUmpParentBusinessTypeConverter());
        registry.addConverter(getVocShopToStringConverter());
        registry.addConverter(getIdToVocShopConverter());
        registry.addConverter(getStringToVocShopConverter());
        /*
        registry.addConverter(getVocGoodsToStringConverter());
        registry.addConverter(getIdToVocGoodsConverter());
        registry.addConverter(getStringToVocGoodsConverter());
        registry.addConverter(getVocGoodsPropertyToStringConverter());
        registry.addConverter(getIdToVocGoodsPropertyConverter());
        registry.addConverter(getStringToVocGoodsPropertyConverter());
        registry.addConverter(getVocSkuToStringConverter());
        registry.addConverter(getIdToVocSkuConverter());
        registry.addConverter(getStringToVocSkuConverter());
        registry.addConverter(getVocTemplateToStringConverter());
        registry.addConverter(getIdToVocTemplateConverter());
        registry.addConverter(getStringToVocTemplateConverter());
        registry.addConverter(getVocTemplateCategoryToStringConverter());
        registry.addConverter(getIdToVocTemplateCategoryConverter());
        registry.addConverter(getStringToVocTemplateCategoryConverter());
        registry.addConverter(getVocWordCategoryToStringConverter());
        registry.addConverter(getIdToVocWordCategoryConverter());
        registry.addConverter(getStringToVocWordCategoryConverter());
        registry.addConverter(getVocWordExpressionsToStringConverter());
        registry.addConverter(getIdToVocWordExpressionsConverter());
        registry.addConverter(getStringToVocWordExpressionsConverter());*/
        
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
