<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" cdata-section-elements="desc"/>
    <xsl:strip-space elements="*"/>

    <xsl:template match="/">
        <products>
        <xsl:for-each select="offers/o">
            <product>
                <name><xsl:value-of select="name"/></name>
                <code><xsl:value-of select="product_code"/></code>
                <desc><xsl:value-of select="desc"/></desc>

                <price><xsl:value-of select="price"/></price>
                <price_brutto><xsl:value-of select="price_brutto"/></price_brutto>
                <price_retail>0</price_retail>

                <stock><xsl:value-of select='@stock'/></stock>
                <stock_ext>0</stock_ext>
                <avail><xsl:value-of select='@avail'/></avail>

                <ext_id><xsl:value-of select='@id'/></ext_id>
                <ext_url><xsl:value-of select='@url'/></ext_url>
                <shipping_time></shipping_time>

                <images>
                    <xsl:for-each select="imgs/main">
                            <url><xsl:value-of select='@url'/></url>
                    </xsl:for-each>
                    <xsl:for-each select="imgs/i">
                        <image>
                            <url><xsl:value-of select='@url'/></url>
                        </image>
                    </xsl:for-each>
                </images>
                <attributes>
                    <xsl:for-each select="attrs/a">
                        <attr>
                            <xsl:attribute name="name">
                                <xsl:value-of select='@name'/>
                            </xsl:attribute>
                            <xsl:value-of select='text()'/>
                        </attr>
                    </xsl:for-each>
                </attributes>
            </product>
        </xsl:for-each>
        </products>
    </xsl:template>
</xsl:stylesheet>