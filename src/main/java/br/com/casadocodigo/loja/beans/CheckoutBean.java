package br.com.casadocodigo.loja.beans;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.Compra;
import br.com.casadocodigo.loja.models.Usuario;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Model
public class CheckoutBean {

    private Usuario usuario = new Usuario();

    @Inject
    private CarrinhoCompras carrinho;

    @Inject
    private FacesContext facesContext;

    @Transactional
    public void finalizar(){
        Compra compra = new Compra();
        compra.setUsuario(usuario);
        carrinho.finalizar(compra);

        /*FacesContext.getCurrentInstance();*/
        String contextName = facesContext.getExternalContext().getRequestContextPath();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT); //307
        response.setHeader("Location" , contextName + "/services/pagamento?uuid=" + compra.getUuid());
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
