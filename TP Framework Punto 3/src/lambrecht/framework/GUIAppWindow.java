package lambrecht.framework;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;

public class GUIAppWindow extends BasicWindow {

	Accion accion;
	Map<Integer, Accion> acciones = new HashMap<Integer, Accion>();

	public GUIAppWindow() {
		ArrayList<Window.Hint> hints = new ArrayList<>();
		hints.add(Window.Hint.CENTERED);
		setHints(hints);

		Properties prop = new Properties();
		try (InputStream configuracionArchivo = getClass()
				.getResourceAsStream("/framework/config/config.properties");) {

			prop.load(configuracionArchivo);

			String valor = prop.getProperty("acciones");
			String[] valores = valor.split(";");
			Class<?> clase;
			for (int i = 0; i < valores.length; i++) {
				clase = Class.forName(valores[i]);
				acciones.put(i + 1, (Accion) clase.getDeclaredConstructor().newInstance());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("No se pudo crear una instancia de Accion: ");
		}
		this.ejecutarVentana();

	}

	public final void ejecutarVentana() {
		Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));
		for (Integer clave : acciones.keySet()) {
			Button b = new Button(new String(acciones.get(clave).nombreItemMenu()));
			b.addListener(new ButtonHandler(("data"), acciones.get(clave)));
			mainPanel.addComponent(b);
		}

		mainPanel.addComponent(new Button("Exit", () -> System.exit(0)));

		this.setComponent(mainPanel);
	}

}
