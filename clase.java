import javax.swing.JOptionPane;

public class Main {
    private static Empresa empresa;

    public static void main(String[] args) {

        empresa = new Empresa("DevPlus", "900123456", "Calle 10 # 5-20", "6011234567", "www.devplus.com");

        int opcion;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("Este es el menu de DevPlus:" +
                    "\n Seleccione una opcion:" +
                    "\n 1. Registrar cliente" +
                    "\n 2. Registrar desarrollador" +
                    "\n 3. Registrar servicio adicional" +
                    "\n 4. Registrar proyecto y asignar desarrolladores" +
                    "\n 5. Asociar servicio adicional a un proyecto" +
                    "\n 6. Calcular valor total de un proyecto" +
                    "\n 7. Confirmar proyecto" +
                    "\n 8. Consultar cliente por telefono y validar numero perfecto" +
                    "\n 9. Consultar ingresos por fecha de solicitud" +
                    "\n 10. Listar clientes, desarrolladores y proyectos" +
                    "\n 0. Salir del sistema"));

            switch (opcion) {

                case 1:
                    solicitarInformacionCliente();
                    break;

                case 2:

                    solicitarInformacionDesarrollador();
                    break;

                case 3:
                    solicitarInformacionServicio();
                    break;

                case 4:
                    solicitarInformacionProyecto();
                    break;

                case 5:
                    solicitarAsociarServicio();
                    break;

                case 6:
                    solicitarCalculoValorTotal();
                    break;

                case 7:
                    solicitarConfirmarProyecto();
                    break;

                case 8:
                    solicitarNumeroPerfecto();
                    break;

                case 9:
                    solicitarIngresosPorFecha();
                    break;

                case 10:

                    String mensaje = empresa.mostrarListaGeneral();
                    JOptionPane.showMessageDialog(null, mensaje);
                    break;

                case 0:
                    JOptionPane.showMessageDialog(null, "El programa finalizo");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "La opcion no es valida");
                    break;
            }

        } while (opcion != 0);

    }

    private static void solicitarInformacionCliente() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre completo o razon social:");
        String documento = JOptionPane.showInputDialog("Ingrese el documento o NIT:");
        String telefono = JOptionPane.showInputDialog("Ingrese el telefono:");
        String correo = JOptionPane.showInputDialog("Ingrese el correo electronico:");
        String pais = JOptionPane.showInputDialog("Ingrese el pais de procedencia:");

        Cliente cliente = new Cliente(nombre, documento, telefono, correo, pais);
        boolean resultado = empresa.agregarCliente(cliente);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Cliente registrado con exito.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo registrar el cliente, se alcanzo el limite.");
        }

    }

    private static void solicitarInformacionDesarrollador() {
        String codigo = JOptionPane.showInputDialog("Ingrese el codigo del desarrollador:");
        String equipo = JOptionPane.showInputDialog("Ingrese el equipo de trabajo:");
        String nivel = JOptionPane.showInputDialog("Ingrese el nivel (Junior, Semisenior, Senior):");
        int cantMax = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad maxima de proyectos simultaneos:"));
        double tarifa = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la tarifa por dia:"));

        Desarrollador desarrollador = new Desarrollador(codigo, equipo, nivel, cantMax, tarifa, "Disponible");
        boolean resultado = empresa.agregarDesarrollador(desarrollador);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Desarrollador registrado con exito.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo registrar el desarrollador, se alcanzo el limite.");
        }
    }

    private static void solicitarInformacionServicio() {
        String codigo = JOptionPane.showInputDialog("Ingrese el codigo del servicio:");
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del servicio:");
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripcion:");
        double precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio:"));
        boolean disponible = Boolean.parseBoolean(JOptionPane.showInputDialog("Ingrese si esta disponible (true/false):"));

        ServicioAdicional servicio = new ServicioAdicional(codigo, nombre, descripcion, precio, disponible);
        boolean resultado = empresa.agregarServicio(servicio);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Servicio adicional registrado con exito.");
        } else {

            JOptionPane.showMessageDialog(null, "No se pudo registrar el servicio, se alcanzo el limite.");
        }
    }

    private static void solicitarInformacionProyecto() {
        if (empresa.getCantidadClientes() == 0) {
            JOptionPane.showMessageDialog(null, "Debe registrar al menos un cliente antes de crear un proyecto.");
            return;
        }

        String docBuscado = JOptionPane.showInputDialog("Ingrese el documento del cliente:");
        Cliente clienteEncontrado = empresa.buscarClientePorDocumento(docBuscado);

        if (clienteEncontrado == null) {
            JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
            return;
        }

        String codigoProyecto = JOptionPane.showInputDialog("Ingrese el codigo del proyecto:");
        String fechaSolicitud = JOptionPane.showInputDialog("Ingrese la fecha de solicitud (dd/mm/aaaa):");
        String fechaInicio = JOptionPane.showInputDialog("Ingrese la fecha de inicio:");
        String fechaEntrega = JOptionPane.showInputDialog("Ingrese la fecha de entrega:");
        String metodoPago = JOptionPane.showInputDialog("Ingrese el metodo de pago (Tarjeta de credito, Transferencia bancaria, Efectivo):");
        int dias = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de dias de desarrollo:"));

        Proyecto proyecto = new Proyecto(codigoProyecto, fechaSolicitud, fechaInicio, fechaEntrega, metodoPago, dias, clienteEncontrado);

        int cantDev = Integer.parseInt(JOptionPane.showInputDialog("Cuantos desarrolladores desea asignar al proyecto?"));
        for (int i = 0; i < cantDev; i++) {
            String codBuscado = JOptionPane.showInputDialog("Ingrese el codigo del desarrollador a asignar:");
            Desarrollador devEncontrado = empresa.buscarDesarrolladorPorCodigo(codBuscado);


            if (devEncontrado == null) {
                JOptionPane.showMessageDialog(null, "Desarrollador no encontrado.");
            } else if (!devEncontrado.getEstado().equals("Disponible")) {
                JOptionPane.showMessageDialog(null, "El desarrollador no se encuentra disponible en las fechas solicitadas.");
            } else if (empresa.contarProyectosActivos(devEncontrado) >= devEncontrado.getCantidadMaximaProyectos()) {
                JOptionPane.showMessageDialog(null, "El desarrollador ya alcanzo su cantidad maxima de proyectos simultaneos.");
            } else {
                proyecto.agregarDesarrollador(devEncontrado);
                JOptionPane.showMessageDialog(null, "Desarrollador asignado al proyecto.");
            }
        }

        empresa.agregarProyecto(proyecto);
        clienteEncontrado.agregarProyecto(proyecto);

        JOptionPane.showMessageDialog(null, "Proyecto registrado con exito.");
    }

    private static void solicitarAsociarServicio() {
        if (empresa.getCantidadProyectos() == 0 || empresa.getCantidadServicios() == 0) {
            JOptionPane.showMessageDialog(null, "Debe existir al menos un proyecto y un servicio registrado.");
            return;
        }

        String codProyectoBuscado = JOptionPane.showInputDialog("Ingrese el codigo del proyecto:");
        Proyecto proyectoEncontrado = empresa.buscarProyectoPorCodigo(codProyectoBuscado);

        if (proyectoEncontrado == null) {
            JOptionPane.showMessageDialog(null, "Proyecto no encontrado.");
            return;
        }


        String codServicioBuscado = JOptionPane.showInputDialog("Ingrese el codigo del servicio:");
        ServicioAdicional servicioEncontrado = empresa.buscarServicioPorCodigo(codServicioBuscado);

        if (servicioEncontrado == null) {
            JOptionPane.showMessageDialog(null, "Servicio no encontrado.");
            return;
        }

        if (!servicioEncontrado.isDisponibilidad()) {
            JOptionPane.showMessageDialog(null, "El servicio no se encuentra disponible.");
            return;
        }

        proyectoEncontrado.agregarServicio(servicioEncontrado);
        JOptionPane.showMessageDialog(null, "Servicio asociado al proyecto con exito.");
    }

    private static void solicitarCalculoValorTotal() {
        if (empresa.getCantidadProyectos() == 0) {
            JOptionPane.showMessageDialog(null, "No hay proyectos registrados.");
            return;
        }

        String codCalculo = JOptionPane.showInputDialog("Ingrese el codigo del proyecto:");
        Proyecto proyectoCalculo = empresa.buscarProyectoPorCodigo(codCalculo);

        if (proyectoCalculo == null) {
            JOptionPane.showMessageDialog(null, "Proyecto no encontrado.");
            return;
        }

        double descuento = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el porcentaje de descuento a aplicar (0 si no aplica):"));

        double total = proyectoCalculo.calcularValorTotal(descuento);

        JOptionPane.showMessageDialog(null, "Valor total del proyecto: $" + total);
    }

    private static void solicitarConfirmarProyecto() {
        if (empresa.getCantidadProyectos() == 0) {
            JOptionPane.showMessageDialog(null, "No hay proyectos registrados.");
            return;
        }

        String codConfirmar = JOptionPane.showInputDialog("Ingrese el codigo del proyecto a confirmar:");
        Proyecto proyectoConfirmar = empresa.buscarProyectoPorCodigo(codConfirmar);

        if (proyectoConfirmar == null) {
            JOptionPane.showMessageDialog(null, "Proyecto no encontrado.");
            return;
        }

        proyectoConfirmar.setEstado("Confirmado");
        Desarrollador[] devs = proyectoConfirmar.getDesarrolladores();
        for (int i = 0; i < proyectoConfirmar.getCantidadDesarrolladores(); i++) {
            devs[i].setEstado("Ocupado");
        }

        JOptionPane.showMessageDialog(null, "Proyecto confirmado y disponibilidad de desarrolladores actualizada.");
    }

    private static void solicitarNumeroPerfecto() {
        String telBuscado = JOptionPane.showInputDialog("Ingrese el numero de telefono a consultar:");
        Cliente clientePorTel = empresa.buscarClientePorTelefono(telBuscado);


        if (clientePorTel == null) {
            JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
            return;
        }

        long numero;
        try {
            numero = Long.parseLong(telBuscado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El telefono no es un numero valido para analizar.");
            return;
        }

        long suma = 0;
        for (long i = 1; i < numero; i++) {
            if (numero % i == 0) {
                suma = suma + i;
            }
        }

        String resultado = "Cliente encontrado: " + clientePorTel.getNombreCompleto() + "\n";
        if (suma == numero) {
            resultado = resultado + "El numero de telefono es un numero perfecto.";
        } else {
            resultado = resultado + "El numero de telefono NO es un numero perfecto.";
        }

        JOptionPane.showMessageDialog(null, resultado);
    }

    private static void solicitarIngresosPorFecha() {
        String fechaConsulta = JOptionPane.showInputDialog("Ingrese la fecha de solicitud a consultar (dd/mm/aaaa):");

        double ingresosTotales = empresa.calcularIngresosPorFecha(fechaConsulta);

        JOptionPane.showMessageDialog(null, "Ingresos totales para la fecha " + fechaConsulta + ": " + ingresosTotales);
    }

}
