package org.diordna.test;

import org.diordna.xmlformat.XMLFormat;

public class Main {
    public static void main(String... args) {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><alipay><is_success>T</is_success><response name=\"id\"><trade><body>订单：T16102111443568</body><buyer_email>1176760525@qq.com</buyer_email><buyer_id>2088402231747066</buyer_id><discount>0.00</discount><flag_trade_locked>0</flag_trade_locked><gmt_create>2016-10-21 11:45:06</gmt_create><gmt_last_modified_time>2016-10-21 11:45:16</gmt_last_modified_time><gmt_payment>2016-10-21 11:45:17</gmt_payment><is_total_fee_adjust>F</is_total_fee_adjust><operator_role>B</operator_role><out_trade_no>H161021114440198N</out_trade_no><payment_type>1</payment_type><price>0.01</price><quantity>1</quantity><seller_email>pay@gstarcad.com</seller_email><seller_id>2088121424678319</seller_id><subject>的生活费...</subject><time_out>2017-01-20 11:45:17</time_out><time_out_type>finishFPAction</time_out_type><to_buyer_fee>0.00</to_buyer_fee><to_seller_fee>0.01</to_seller_fee><total_fee>0.01</total_fee><trade_no>2016102121001004060245497067</trade_no><trade_status>TRADE_SUCCESS</trade_status><use_coupon>F</use_coupon></trade></response><sign>25217c932f49868371a301e3151201a2</sign><sign_type>MD5</sign_type></alipay>";
        XMLFormat format = new XMLFormat(xml);
        System.out.println(format.format());
    }
}
