package pl.estrix.controller.ui.test;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// is a Spring annotation that defines that a single instance of this class will exist per user.
@Scope(value = "session")

// defines this class as a Spring component and names it as productController—name that will be used in the form's interface.
@Component(value = "productController")

// is an annotation provided by Rewrite that configures the name of the bean on its scope.
@ELBeanName(value = "productController")

// annotation provided by Rewrite—configures the /productURL to respond with the contents of product-form.xhtml
@Join(path = "/secured/product", to = "/secured/product-form.jsf")
public class ProductController {

    private Product product = ProductFactoryHelper.createProduct();

    @Autowired
    ProductServiceApi productServiceApi;

    public String save() {
        productServiceApi.save(product);
        product = ProductFactoryHelper.createProduct();
        return "/product-list.xhtml?faces-redirect=true";
    }

    public Product getProduct() {
        return product;
    }
}
