<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <products>
            <xsl:for-each select="Produkty/Produkt">
                <product>
                    <name><xsl:value-of select="Nazwa_produktu"/></name>
                    <code><xsl:value-of select="Kod_ean"/></code>
                    <desc><xsl:value-of select="Opis"/></desc>

                    <price><xsl:value-of select="Cena_netto"/></price>
                    <price_brutto><xsl:value-of select="Cena_brutto"/></price_brutto>
                    <price_retail>0</price_retail>

                    <stock><xsl:value-of select='Ilosc_produktow'/></stock>
                    <stock_ext>0</stock_ext>
                    <avail>1</avail>

                    <ext_id>0</ext_id>
                    <ext_url></ext_url>
                    <shipping_time><xsl:value-of select='Termin_wysylki'/></shipping_time>

                    <images>

                        <url><xsl:value-of select='Zdjecie_glowne'/></url>
                        <xsl:for-each select="Zdjecia_dodatkowe/Zdjecie">
                            <url><xsl:value-of select='Zdjecie_link'/></url>
                        </xsl:for-each>
                    </images>
                    <attributes></attributes>
                </product>
            </xsl:for-each>
        </products>
    </xsl:template>

</xsl:stylesheet>