package org.diordna.xmlformat;

import org.diordna.xmlformat.exception.IllegalFormatException;
import org.diordna.xmlformat.utils.StringUtils;

/**
 * 格式化xml字符串
 * @author orange
 */
public class XMLFormat {
    private String content; //xml字符串
    public XMLFormat(String content) {
        this.content = content;
    }

    public String format() {
        String header = getHeader();
        return (StringUtils.isEmpty(header) ? "" : header)
                + format(null, content, 0);
    }

    private String format(String tag, String content, int depth) {
        String format = "";
        String firstTag = "";
        if (StringUtils.isEmpty(tag)) {
            firstTag = getFirstTag(content);
        } else {
            firstTag = tag;
        }

        String inside = getInsideContent(firstTag, content);
        String outside = getOutsideContent(firstTag, content);

        String insideTag = "";
        try {
            insideTag = getFirstTag(inside);
        } catch (Exception e) {
            insideTag = null;
        }
        if (StringUtils.isEmpty(insideTag)) {
            format = "\r\n" + indent(depth) + "<" + firstTag + ">"
                    + inside + "</" + firstTag.split(" ")[0] + ">";
        } else {
            format = "\r\n" + indent(depth) + "<" + firstTag + ">"
                    + format(insideTag, inside, depth + 1)
                    + indent(depth) + "</" + firstTag.split(" ")[0] + ">";
        }

        String outsideTag = "";
        if (StringUtils.isEmpty(outside)) {
            outsideTag = null;
        } else {
            outsideTag = getFirstTag(outside);
        }
        if (!StringUtils.isEmpty(outsideTag)) {
            format += indent(depth) + format(outsideTag, outside, depth);
        } else if (StringUtils.isEmpty(outside)) {
            format += "\r\n";
        } else {
            throw new IllegalFormatException("xml报文格式不正确");
        }
        return format;
    }

    /**
     * 获取xml头部数据，格式：<? …… ?>
     * @return xml头部数据，null表示不存在
     */
    private String getHeader() {
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == ' ' || c == '\r' || c == '\n'
                    || c == '\t') {
                continue;
            }

            if (c == '<' && content.charAt(i + 1) == '?') {
                String header = "<?";
                for (i = i + 2; i < content.length(); i++) {
                    char end = content.charAt(i);
                    if (end == '?' && content.charAt(i + 1) == '>') {
                        header += "?>";
                        content = content.substring(i + 2);
                        return header;
                    } else {
                        header += end;
                    }
                }
            }

            return null;
        }

        return null;
    }

    /**
     * 获取xml报文的第一个标签
     * @param content xml报文
     * @return 标签名称
     */
    private String getFirstTag(String content) {
        StringBuilder tag = new StringBuilder();
        int index = 0;

        for (; index < content.length(); index++) {
            char temp = content.charAt(index);
            if (temp == ' ' || temp == '\r' || temp == '\n'
                    || temp == '\t') { //忽略空格回车字符
                continue;
            }

            if (temp != '<') {
                throw new IllegalFormatException("xml报文格式不正确");
            }
            break;
        }

        for (int i = index + 1; i < content.length(); i++) {

            char c = content.charAt(i);
            if (c == '>') {
                return tag.toString();
            }
            tag.append(c);
        }
        throw new IllegalFormatException("xml报文格式不正确");
    }

    private String getOutsideContent(String tag, String content) {
        String endTag = "</" + tag.split(" ")[0] + ">";
        int endIndex = content.indexOf(endTag) + endTag.length();

        return content.substring(endIndex);
    }

    private String getInsideContent(String tag, String content) {
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag.split(" ")[0] + ">";

        int startIndex = content.indexOf(startTag) + startTag.length();
        int endIndex = content.indexOf(endTag);

        return content.substring(startIndex, endIndex);
    }

    private String indent(int num) {
        String space = "";
        if (num == 0) {
            return space;
        } else {
            return space + PER_SPACE + indent(num - 1);
        }
    }

    private static final String PER_SPACE = "    "; //缩进字符串
}
