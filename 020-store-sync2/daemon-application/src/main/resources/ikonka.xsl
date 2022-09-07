<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <products>
            <xsl:for-each select="produkty/produkt">
                <product>
                    <name><xsl:value-of select="nazwa"/></name>
                    <code><xsl:value-of select="kod"/></code>
                    <desc><xsl:value-of select="opis"/></desc>

                    <price>0</price>
                    <price_brutto><xsl:value-of select="cena"/></price_brutto>
                    <price_retail><xsl:value-of select="sugerowana_cena_detaliczna"/></price_retail>

                    <stock><xsl:value-of select='stan'/></stock>
                    <stock_ext>0</stock_ext>
                    <avail>1</avail>

                    <ext_id>0</ext_id>
                    <ext_url></ext_url>
                    <shipping_time><xsl:value-of select='czas_dostawy'/></shipping_time>

                    <images>
                        <xsl:for-each select="zdjecia/zdjecie">
                            <url><xsl:value-of select='text()'/></url>
                        </xsl:for-each>
                    </images>
                    <attributes></attributes>
                </product>
            </xsl:for-each>
        </products>
    </xsl:template>

</xsl:stylesheet>