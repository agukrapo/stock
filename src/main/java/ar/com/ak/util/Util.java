package ar.com.ak.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javax.faces.model.SelectItem;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

public class Util {

    public static final String TRUCATE_STRING_TERNMINATOR = "(...)";
    public static final String UTF_8 = "UTF-8";

    private Util() {
    }

//	public static String makeSystemKey() {
//
//		Calendar calendar = Calendar.getInstance();
//		
//		int month = calendar.get(Calendar.MONTH) + 1;
//		
//		String key = null;
//		
//		if (month <= 4) {
//			key = DigestUtils.sha512Hex(String.format(
//					Constants.SYSTEM_STRING_KEY + "%d%d", 4, calendar
//							.get(Calendar.YEAR)));
//		} else if (month <= 8) {
//			key = DigestUtils.sha512Hex(String.format(
//					Constants.SYSTEM_STRING_KEY + "%d%d", 8, calendar
//							.get(Calendar.YEAR)));
//		} else if (month <= 12) {
//			key = DigestUtils.sha512Hex(String.format(
//					Constants.SYSTEM_STRING_KEY + "%d%d", 12, calendar
//							.get(Calendar.YEAR)));
//		} 
//
//		return key;
//	}	

    public static List<String> loadResourceValues(ResourceBundle resourceBundle) {
        List<String> result = new ArrayList<String>();

        Enumeration<String> keys = resourceBundle.getKeys();

        while (keys.hasMoreElements()) {
            result.add(resourceBundle.getString(keys.nextElement()));
        }

        return result;
    }

    public static String replaceAccentedChars(String string) {
        String result = null;

        if (string != null) {
            char[] find = {'Ć', 'Á', 'É', 'Í', 'Ó', 'Ú', 'Á', 'É', 'Í', 'Ó', 'Ú', ' ', 'Á', 'É', 'Í', 'Ó', 'Ú', '\''};
            char[] replace = {'C', 'A', 'E', 'I', 'O', 'U', 'A', 'E', 'I', 'O', 'U', ' ', 'A', 'E', 'I', 'O', 'U', ' '};

            for (int i = 0; i < find.length; i++) {
                result = string.replaceAll(String.valueOf(find[i]),
                        String.valueOf(replace[i]));

                result = string.replaceAll(String.valueOf(find[i]).toLowerCase(),
                        String.valueOf(replace[i]).toLowerCase());
            }

            return result.trim().replaceAll("\\p{Space}+", " ");
        }

        return result;
    }

    public static String trim(String string) {
        String result = null;

        if (string != null) {
            result = string.trim().replaceAll("\\p{Space}+", " ");
        }

        return result;
    }

    public static String propperFormat(String value) {
        String result = "";

        if (value != null) {
            String[] strings = StringUtils.split(trim(value), " ");
            for (int i = 0; i < strings.length; i++) {
                if (!(strings[i].startsWith("\"") && strings[i].endsWith("\""))) {
                    strings[i] = StringUtils.capitalize(strings[i]);
                }
            }

            result = StringUtils.join(strings, " ");
        }

        return result;
    }

    public static void invokeSetMethod(Object bean, String propertyName, Object propertyValue, Class<?> propertyClass)
            throws IllegalArgumentException, SecurityException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        bean.getClass().getMethod(setMethodName(propertyName), propertyClass)
                .invoke(bean, propertyValue);
    }

    public static Object invokeGet(Object bean, String propertyName)
            throws IllegalArgumentException, SecurityException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        return bean.getClass().getMethod(getterMethodName(propertyName)).invoke(bean);
    }

    public static String invokeGetString(Object bean, String propertyName)
            throws IllegalArgumentException, SecurityException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        return bean.getClass().getMethod(getterMethodName(propertyName)).invoke(bean).toString();
    }

    public static String setMethodName(String field) {
        return "set" + StringUtils.capitalize(field);
    }

    public static String getterMethodName(String field) {
        return "get" + StringUtils.capitalize(field);
    }

    public static <T> List<SelectItem> populateSelectItems(List<T> values) {
        List<SelectItem> result = new ArrayList<SelectItem>();

        try {
            for (Object content : values) {
                result.add(new SelectItem(content));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<SelectItem> populateSelectItemsValueOnly(List<?> values) {
        List<SelectItem> result = new ArrayList<SelectItem>();

        try {
            for (Object content : values) {
                result.add(new SelectItem(invokeGet(content, "nombre")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<SelectItem> populateSelectItemsByEnum(Class<?> enumClass,
                                                             String resourceLocation) {

        ResourceBundle resource = ResourceBundle.getBundle(resourceLocation);

        List<SelectItem> result = new ArrayList<SelectItem>();

        try {
            for (Object content : enumClass.getEnumConstants()) {
                result.add(new SelectItem(content, resource.getString(content.toString())));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<String> populateNamesList(List<?> values) {
        List<String> result = new ArrayList<String>();

        result.add("");

        try {
            for (Object content : values) {
                result.add(invokeGetString(content, "nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String truncateString(String value, int maxLength) {
        String result = value;

        if (value.length() > maxLength) {
            result = value.substring(0, maxLength - TRUCATE_STRING_TERNMINATOR.length())
                    + TRUCATE_STRING_TERNMINATOR;
        }

        return result;
    }

    public static Integer calculateAge(Date birthDate) {

        Integer result = null;

        if (birthDate != null) {
            Calendar birth = Calendar.getInstance();
            Calendar now = Calendar.getInstance();

            birth.setTime(birthDate);

            result = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

            if ((birth.get(Calendar.MONTH) > now.get(Calendar.MONTH))
                    || (birth.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                    && birth.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH))) {

                result--;
            }
        }

        return result;
    }

    public static List<String> getPropertiesValues(String location) {
        ResourceBundle resource = ResourceBundle.getBundle(location);

        Enumeration<String> keys = resource.getKeys();

        List<String> result = new ArrayList<String>();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            result.add(resource.getString(key));
        }

        return result;
    }

    public static <T> List<T> collectionToList(Collection<T> collection) {
        List<T> result = new ArrayList<T>();

        result.addAll(collection);

        return result;
    }

    public static <T> Set<T> collectionToSet(Collection<T> collection) {
        Set<T> result = new HashSet<T>();

        result.addAll(collection);

        return result;
    }

    public static List<Class<?>> getClasses(String packageName) {
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        try {
            ClassLoader classLoader = Thread.currentThread()
                    .getContextClassLoader();

            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            List<File> dirs = new ArrayList<File>();

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                String fileName = resource.getFile();
                String fileNameDecoded = URLDecoder.decode(fileName, UTF_8);
                dirs.add(new File(fileNameDecoded));
            }

            classes = new ArrayList<Class<?>>();
            for (File directory : dirs) {
                classes.addAll(findClasses(directory, packageName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classes;
    }

    public static List<Class<?>> findClasses(File directory, String packageName)
            throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + fileName));
            } else if (fileName.endsWith(".class") && !fileName.contains("$")) {
                Class<?> clazz;
                try {
                    clazz = Class.forName(packageName + '.'
                            + fileName.substring(0, fileName.length() - 6));
                } catch (ExceptionInInitializerError e) {
                    clazz = Class.forName(packageName + '.'
                                    + fileName.substring(0, fileName.length() - 6),
                            false, Thread.currentThread()
                                    .getContextClassLoader());
                }
                classes.add(clazz);
            }
        }
        return classes;
    }

    public static String abbreviateString(String value) {
        String result = null;

        if (value != null) {
            String[] strings = StringUtils.split(trim(value), " ");
            for (int i = 0; i < strings.length; i++) {
                strings[i] = "" + StringUtils.capitalize(strings[i]).charAt(0);
            }

            result = StringUtils.join(strings, "");
        }

        return result;
    }

    public static Integer doubleToInteger(Double value) {
        return value != null ? Integer.valueOf((int) value.doubleValue()) : null;
    }

    @SuppressWarnings("unchecked")
    public static void logFatalThrowable(Throwable throwable, Logger log) {
        List<Throwable> throwableList = ExceptionUtils.getThrowableList(throwable);

        log.fatal(MessageFactory.getMessage(MessageFactory.ERROR_FATAL) + throwableList.size());

        for (Throwable t : throwableList) {
            log.fatal(t);
        }
    }

    public static Integer getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }

    public static Integer getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.MONTH) + 1;
    }

    public static Integer getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String itaratorToString(Iterator<?> iterator) {
        ToStringBuilder builder = new ToStringBuilder(iterator);

        while (iterator.hasNext()) {
            builder.append(iterator.next());
        }

        return builder.toString();
    }

}
