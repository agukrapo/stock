package ar.com.ak.util;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Collection;

public class Check {

    private Check() {
    }

    public static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }

    public static boolean isEmpty(Collection<?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !"".equals(value);
    }

    public static boolean isNotEmpty(Collection<?> value) {
        return value != null && !value.isEmpty();
    }

    public static boolean isEqual(Object value1, Object value2) {
        return value1 != null && value1.equals(value2);
    }

    public static boolean isEmptyBean(Object bean) {
        boolean result = true;

        try {
            BeanUtils.describe(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

//	public static boolean systemUpToDate() {
//		boolean result = false;
//		
//		try {
//			Calendar calendar = Calendar.getInstance();
//			
//			int month = calendar.get(Calendar.MONTH) + 1;
//			
//			String key = null;
//			
//			if (month <= 4) {
//				key = DigestUtils.sha512Hex(String.format(
//						Constants.SYSTEM_STRING_KEY + "%d%d", 4, calendar
//								.get(Calendar.YEAR)));
//			} else if (month <= 8) {
//				key = DigestUtils.sha512Hex(String.format(
//						Constants.SYSTEM_STRING_KEY + "%d%d", 8, calendar
//								.get(Calendar.YEAR)));
//			} else if (month <= 12) {
//				key = DigestUtils.sha512Hex(String.format(
//						Constants.SYSTEM_STRING_KEY + "%d%d", 12, calendar
//								.get(Calendar.YEAR)));
//			}
//
//			ApplicationContextFacade context = (ApplicationContextFacade) FacesContext
//					.getCurrentInstance().getExternalContext().getContext();
//	
//			File keyFile = new File(context.getRealPath("WEB-INF/classes/" + Constants.SYSTEM_KEY_FILE_NAME));
//		
//			if (keyFile.exists()) {
//				String keyFileContent = FileUtils.readFileToString(keyFile);
//
//				result = key.equals(keyFileContent);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return result;
//	}

    public static String exceptionIfEmpty(String value) throws NullPointerException {
        String result = null;

        if (Check.isEmpty(value)) {
            throw new NullPointerException();
        } else {
            result = value;
        }

        return result;
    }

    public static String exceptionIfEmpty(Object value) throws NullPointerException {
        String result = null;

        if (value == null) {
            throw new NullPointerException();
        } else {
            result = value.toString();
        }

        return result;
    }
}
