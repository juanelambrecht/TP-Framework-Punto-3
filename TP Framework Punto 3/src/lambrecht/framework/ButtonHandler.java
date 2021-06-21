package lambrecht.framework;

import java.io.IOException;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Button.Listener;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class ButtonHandler implements Listener {
	final String loc;
	Accion accion;
	ButtonHandler(String loc,Accion accion) {
		this.loc = loc;
		this.accion = accion;
	}

	public void onTriggered(Button button) {

		 Terminal terminal;
			try {
				terminal = new DefaultTerminalFactory().createTerminal();

				try (Screen screen = new TerminalScreen(terminal)) {
					String accionItem =  this.accion.nombreItemMenu();
					String descripcion = this.accion.descripcionItemMenu();
					TextGraphics tGraphics = screen.newTextGraphics();

					screen.startScreen();
					screen.clear();
					terminal.setBackgroundColor(TextColor.ANSI.BLUE);
					tGraphics.putString(10, 10, accionItem);
					tGraphics.putString(10, 13, descripcion);
					this.accion.ejecutar();
					screen.refresh();

					screen.readInput();
					screen.stopScreen();
				}
			} catch (IOException e) {
				throw new RuntimeException("No se pudo agregar la informacion a la terminal");
			}
			
	}


}
