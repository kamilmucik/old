<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" cdata-section-elements="desc"/>
    <xsl:strip-space elements="*"/>

    <xsl:template match="/">
        <products>
        <xsl:for-each select="products/product">
            <product>
                <name><xsl:value-of select="name"/></name>
                <code><xsl:value-of select="product_code"/></code>
                <desc><xsl:value-of select="description"/></desc>

                <price><xsl:value-of select="price_net"/></price>
                <price_brutto><xsl:value-of select="price_gross"/></price_brutto>
                <price_retail><xsl:value-of select="price_retail"/></price_retail>

                <stock><xsl:value-of select='in_stock'/></stock>
                <stock_ext><xsl:value-of select='stock_zew'/></stock_ext>
                <avail><xsl:value-of select='active'/></avail>

                <ext_id><xsl:value-of select='id'/></ext_id>
                <ext_url></ext_url>
                <shipping_time><xsl:value-of select='shipping/shipping_time'/></shipping_time>

                <images>
                    <xsl:for-each select="images/image">
                            <url><xsl:value-of select='text()'/></url>
                    </xsl:for-each>
                </images>
                <attributes></attributes>
            </product>
        </xsl:for-each>
        </products>
    </xsl:template>

</xsl:stylesheet>