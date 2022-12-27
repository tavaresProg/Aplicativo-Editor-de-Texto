package br.com.editor.classes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea areaParaEscrever;
	private JScrollPane scroll;
	private JLabel labelDaFonte;
	private JSpinner spinnerDoTamanhoDaFonte;
	private JButton botaoDaCorDaFonte;
	private JComboBox<?> caixaDaFonte;

	private JMenuBar barraDoMenu;
	private JMenu arquivoMenu;
	private JMenuItem abrir;
	private JMenuItem salvar;
	private JMenuItem sair;

	public TextEditor() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Editor de Texto por tavaresProg");
		this.setSize(500, 500);
		this.setLayout(new FlowLayout());
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(new Color(235, 180, 52));

		areaParaEscrever = new JTextArea();
		areaParaEscrever.setLineWrap(true);
		areaParaEscrever.setWrapStyleWord(true);
		areaParaEscrever.setFont(new Font("Arial", Font.PLAIN, 20));

		scroll = new JScrollPane(areaParaEscrever);
		scroll.setPreferredSize(new Dimension(450, 400));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		labelDaFonte = new JLabel("Tamanho da Fonte: ");

		spinnerDoTamanhoDaFonte = new JSpinner();
		spinnerDoTamanhoDaFonte.setPreferredSize(new Dimension(50, 25));
		spinnerDoTamanhoDaFonte.setValue(20);
		spinnerDoTamanhoDaFonte.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				areaParaEscrever.setFont(new Font(areaParaEscrever.getFont().getFamily(), Font.PLAIN,
						(int) spinnerDoTamanhoDaFonte.getValue()));

			}

		});

		botaoDaCorDaFonte = new JButton("Cor da Fonte");
		botaoDaCorDaFonte.setBackground(Color.WHITE);
		botaoDaCorDaFonte.addActionListener(this);

		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		caixaDaFonte = new JComboBox(fonts);
		caixaDaFonte.setRenderer(new MyComboBoxRenderer("Selecione uma fonte"));
		caixaDaFonte.setSelectedIndex(-1);
		caixaDaFonte.addActionListener(this);

		barraDoMenu = new JMenuBar();
		arquivoMenu = new JMenu("Arquivo");
		abrir = new JMenuItem("Abrir");
		salvar = new JMenuItem("Salvar");
		sair = new JMenuItem("Sair");

		abrir.addActionListener(this);
		salvar.addActionListener(this);
		sair.addActionListener(this);

		arquivoMenu.add(abrir);
		arquivoMenu.add(salvar);
		arquivoMenu.add(sair);
		barraDoMenu.add(arquivoMenu);

		this.setJMenuBar(barraDoMenu);
		this.add(scroll);
		this.add(labelDaFonte);
		this.add(spinnerDoTamanhoDaFonte);
		this.add(botaoDaCorDaFonte);
		this.add(caixaDaFonte);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == botaoDaCorDaFonte) {

			Color color = JColorChooser.showDialog(null, "Escolha uma Cor", Color.black);
			areaParaEscrever.setForeground(color);

		}

		if (e.getSource() == caixaDaFonte) {
			areaParaEscrever.setFont(new Font((String) caixaDaFonte.getSelectedItem(), Font.PLAIN,
					areaParaEscrever.getFont().getSize()));
		}

		if (e.getSource() == abrir) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(null);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Documentos de Texto", "txt");
			fileChooser.setFileFilter(filter);

			int response = fileChooser.showOpenDialog(null);

			if (response == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;

				try {
					fileIn = new Scanner(file);
					if (file.isFile()) {
						while (fileIn.hasNextLine()) {
							String line = fileIn.nextLine() + "\n";
							areaParaEscrever.append(line);
						}
					}
				} catch (FileNotFoundException e1) {

					e1.printStackTrace();
				} finally {
					fileIn.close();
				}
			}

		}

		if (e.getSource() == salvar) {
			JOptionPane.showMessageDialog(null, "Coloque .txt no final do arquivo.");
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(null);

			int response = fileChooser.showSaveDialog(null);

			if (response == JFileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter fileOut = null;

				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(areaParaEscrever.getText());
				} catch (FileNotFoundException e1) {

					e1.printStackTrace();
				} finally {
					fileOut.close();
				}
			}

		}

		if (e.getSource() == sair) {
			System.exit(0);
		}

	}

}
