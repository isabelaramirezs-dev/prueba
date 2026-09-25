public class Empresa {
    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String paginaWeb;
    private Cliente[] clientes;
    private int cantidadClientes;
    private Desarrollador[] desarrolladores;
    private int cantidadDesarrolladores;
    private Proyecto[] proyectos;
    private int cantidadProyectos;
    private ServicioAdicional[] servicios;
    private int cantidadServicios;

    public Empresa(String nombreComercial, String nit, String direccion, String telefono, String paginaWeb) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.clientes = new Cliente[100];
        this.cantidadClientes = 0;
        this.desarrolladores = new Desarrollador[100];
        this.cantidadDesarrolladores = 0;
        this.proyectos = new Proyecto[100];
        this.cantidadProyectos = 0;
        this.servicios = new ServicioAdicional[100];
        this.cantidadServicios = 0;
    }

    public String getNombreComercial() {

        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPaginaWeb() {

        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public Cliente[] getClientes() {
        return clientes;
    }

    public void setClientes(Cliente[] clientes) {
        this.clientes = clientes;
    }

    public int getCantidadClientes() {
        return cantidadClientes;
    }

    public void setCantidadClientes(int cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }

    public Desarrollador[] getDesarrolladores() {
        return desarrolladores;
    }

    public void setDesarrolladores(Desarrollador[] desarrolladores) {
        this.desarrolladores = desarrolladores;
    }

    public int getCantidadDesarrolladores() {

        return cantidadDesarrolladores;
    }

    public void setCantidadDesarrolladores(int cantidadDesarrolladores) {
        this.cantidadDesarrolladores = cantidadDesarrolladores;
    }

    public Proyecto[] getProyectos() {
        return proyectos;
    }

    public void setProyectos(Proyecto[] proyectos) {
        this.proyectos = proyectos;
    }

    public int getCantidadProyectos() {
        return cantidadProyectos;
    }

    public void setCantidadProyectos(int cantidadProyectos) {
        this.cantidadProyectos = cantidadProyectos;
    }

    public ServicioAdicional[] getServicios() {
        return servicios;
    }

    public void setServicios(ServicioAdicional[] servicios) {
        this.servicios = servicios;
    }

    public int getCantidadServicios() {

        return cantidadServicios;
    }

    public void setCantidadServicios(int cantidadServicios) {
        this.cantidadServicios = cantidadServicios;
    }

    public boolean agregarCliente(Cliente cliente) {
        if (cantidadClientes < clientes.length) {
            clientes[cantidadClientes] = cliente;
            cantidadClientes++;
            return true;
        }
        return false;
    }

    public boolean agregarDesarrollador(Desarrollador desarrollador) {
        if (cantidadDesarrolladores < desarrolladores.length) {
            desarrolladores[cantidadDesarrolladores] = desarrollador;
            cantidadDesarrolladores++;
            return true;
        }
        return false;
    }

    public boolean agregarProyecto(Proyecto proyecto) {
        if (cantidadProyectos < proyectos.length) {
            proyectos[cantidadProyectos] = proyecto;
            cantidadProyectos++;
            return true;
        }
        return false;

    }

    public boolean agregarServicio(ServicioAdicional servicio) {
        if (cantidadServicios < servicios.length) {
            servicios[cantidadServicios] = servicio;
            cantidadServicios++;
            return true;
        }
        return false;
    }

    public Cliente buscarClientePorDocumento(String documento) {
        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getDocumentoIdentidad().equals(documento)) {
                return clientes[i];
            }
        }
        return null;
    }

    public Cliente buscarClientePorTelefono(String telefono) {
        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getTelefono().equals(telefono)) {
                return clientes[i];
            }
        }
        return null;
    }

    public Desarrollador buscarDesarrolladorPorCodigo(String codigo) {
        for (int i = 0; i < cantidadDesarrolladores; i++) {
            if (desarrolladores[i].getCodigo().equals(codigo)) {

                return desarrolladores[i];
            }
        }
        return null;
    }

    public Proyecto buscarProyectoPorCodigo(String codigo) {
        for (int i = 0; i < cantidadProyectos; i++) {
            if (proyectos[i].getCodigoProyecto().equals(codigo)) {
                return proyectos[i];
            }
        }
        return null;
    }

    public ServicioAdicional buscarServicioPorCodigo(String codigo) {
        for (int i = 0; i < cantidadServicios; i++) {
            if (servicios[i].getCodigo().equals(codigo)) {
                return servicios[i];
            }
        }
        return null;
    }

    public int contarProyectosActivos(Desarrollador desarrollador) {
        int contador = 0;
        for (int i = 0; i < cantidadProyectos; i++) {
            Proyecto p = proyectos[i];
            if (p.tieneDesarrollador(desarrollador) && !p.getEstado().equals("Finalizado") && !p.getEstado().equals("Cancelado")) {
                contador++;
            }
        }

        return contador;
    }

    public double calcularIngresosPorFecha(String fecha) {
        double total = 0;
        for (int i = 0; i < cantidadProyectos; i++) {
            if (proyectos[i].getFechaSolicitud().equals(fecha)) {
                total = total + proyectos[i].getValorTotal();
            }
        }
        return total;
    }

    public String mostrarListaGeneral() {
        String resultado = "--- Clientes ---\n";
        for (int i = 0; i < cantidadClientes; i++) {
            resultado = resultado + clientes[i].getNombreCompleto() + " - " + clientes[i].getDocumentoIdentidad() + " - " + clientes[i].getTelefono() + "\n";
        }
        resultado = resultado + "--- Desarrolladores ---\n";
        for (int i = 0; i < cantidadDesarrolladores; i++) {
            resultado = resultado + desarrolladores[i].getCodigo() + " - " + desarrolladores[i].getNivel() + " - " + desarrolladores[i].getEstado() + "\n";
        }
        resultado = resultado + "--- Proyectos ---\n";
        for (int i = 0; i < cantidadProyectos; i++) {
            resultado = resultado + proyectos[i].getCodigoProyecto() + " - " + proyectos[i].getEstado() + " - $" + proyectos[i].getValorTotal() + "\n";
        }
        return resultado;
    }
}