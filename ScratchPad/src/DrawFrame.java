import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class DrawFrame extends JFrame implements MouseMotionListener, Runnable {
	private static final long serialVersionUID = 1L;
	
	private Image dbi;
	private Graphics dbg;
	
	Image imported = null;
	
	ArrayList<Rectangle> px = new ArrayList<Rectangle>();
	ArrayList<Integer> colors = new ArrayList<Integer>();
	
	ArrayList<Rectangle> x_o = new ArrayList<Rectangle>();
	//ArrayList<Integer> sizes = new ArrayList<Integer>();
	
	Rectangle canvas;
	Rectangle mouse = new Rectangle(0, 0, 3, 3);
	
	int mx;
	int my;
	
	@SuppressWarnings("rawtypes")
	JComboBox tool = new JComboBox();
	@SuppressWarnings("rawtypes")
	JComboBox color = new JComboBox();
	//@SuppressWarnings("rawtypes")
	//JComboBox toolset = new JComboBox();
	
	JTextField size = new JTextField("3");
	
	JButton newDoodle = new JButton("New");
	JButton save = new JButton("Save");
	JButton open = new JButton("Open...");
	
	JMenuBar menubar = new JMenuBar();
	
	JMenu file = new JMenu("File");
	
	JLabel l0 = new JLabel("Brush Size:");
	
	@SuppressWarnings("unchecked")
	public DrawFrame() {
		
		setSize(480, 480);
		
		canvas = new Rectangle(0, 75, getWidth(), getHeight() - 75);
		
		setTitle("ScratchPad _50");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setBackground(Color.WHITE);
		
		menubar.add(file);
		
		tool.addItem("Pen");
		tool.addItem("Eraser");
		
		//toolset.addItem("Square");
		//toolset.addItem("Ellipse");
		//toolset.addItem("Rounded Square");
		//toolset.addItem("Calligraphy Brush");
		
		color.addItem("Black");
		color.addItem("Red");
		color.addItem("Blue");
		
		add(open);
		add(newDoodle);
		add(save);
		add(tool);
		add(color);
		add(l0);
		add(size);
		
		setVisible(true);
		
		addMouseMotionListener(this);
		
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser opener = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "Jpg Images", "jpg", "Portable Network Graphics","png");
				    opener.setFileFilter(filter);
				    int returnVal = opener.showOpenDialog(null);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				            File f = new File(opener.getSelectedFile().getAbsolutePath());
				            try {
								imported = ImageIO.read(f);
							} catch (IOException e) {
								System.out.println("Error while reading file " + f.getAbsolutePath() + "!");
								e.printStackTrace();
							}				            
				    }	
				
			}
						
		});
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				
				JFileChooser saver = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "Jpg Images", "jpg");
				    saver.setFileFilter(filter);
				    int returnVal = saver.showSaveDialog(null);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				            File f = new File(saver.getSelectedFile().getAbsolutePath() + ".jpg");
				            try {
								new Graphic(f, dbi);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				    }

				
			}
			
		});
		
		newDoodle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				
				int op = JOptionPane.showConfirmDialog(null, "Erase all items and create a new drawing?", "Reset?", JOptionPane.YES_NO_OPTION);
				
				if (op == 0) {
					
					px.clear();
					colors.clear();
					x_o.clear();
					
					imported = null;
					
				}
				
			}
			
		});
		
	}

	/*@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DrawFrame df = new DrawFrame();
		Thread t = new Thread(df);
		
		t.start();

	}
	
	public void paint(Graphics g) {
		
		dbi = createImage(getWidth(), getHeight());
		dbg = dbi.getGraphics();
		draw(dbg);
		g.drawImage(dbi, 0, 0, null);
		
	}

	private void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		super.paint(g);
		
		g.setColor(Color.WHITE);
		//g.fillRect(canvas.x, canvas.y, canvas.width, canvas.height);
		
		g.setColor(Color.BLACK);
		
		if (imported != null) {
			
			g.drawImage(imported, 0, 80, null);	
			
		}
		
		for (int i = 0; i < px.size(); i++) {
			
			if (colors.get(i) == 0) {
				g.setColor(Color.BLACK);
				g.fillRect(px.get(i).x, px.get(i).y, px.get(i).width, px.get(i).height);
			} else if (colors.get(i) == 1) {
				g.setColor(Color.RED);
				g.fillRect(px.get(i).x, px.get(i).y, px.get(i).width, px.get(i).height);
			} else if (colors.get(i) == 2) {
				g.setColor(Color.BLUE);
				g.fillRect(px.get(i).x, px.get(i).y, px.get(i).width, px.get(i).height);				
			}
			
		}
		
		g.setColor(Color.BLUE);
		g.drawString("(" + mx + ", " + my + ")", mx + 10, my - 5);
		g.drawRect(mouse.x, mouse.y, mouse.width, mouse.height);
		
		repaint();
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(true) {
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		// TODO Auto-generated method stub
		
		int x = m.getX();
		int y = m.getY();
		
		mouse = new Rectangle(x, y, Integer.parseInt(size.getText()), Integer.parseInt(size.getText()));
		
		if (mouse.intersects(canvas)) {
		
			if (tool.getSelectedItem().equals("Pen")) {
		
				px.add(new Rectangle(x, y, Integer.parseInt(size.getText()), Integer.parseInt(size.getText())));
			
				if (color.getSelectedItem().equals("Black")) {
				
					colors.add(0);
				
				}
			
				if (color.getSelectedItem().equals("Red")) {
				
					colors.add(1);
				
				}

				if (color.getSelectedItem().equals("Blue")) {
	
					colors.add(2);
	
				}
		
			}
		
			if (tool.getSelectedItem().equals("Eraser")) {
			
				x_o = px;
				//y_o = py;
			
				//py.clear();
			
				for(int i = 0; i < px.size(); i++) {
				
					if (mouse.intersects(px.get(i))) {
					
						x_o.remove(i);
						colors.remove(i);
						continue;
					
					}
				
				}
			
				px = x_o;
			
			}
		
		}
		
		mx = x;
		my = y;
		
	}

	@Override
	public void mouseMoved(MouseEvent m) {
		// TODO Auto-generated method stub
		
		int x = m.getX();
		int y = m.getY();
		
		mouse = new Rectangle(mx, my, Integer.parseInt(size.getText()), Integer.parseInt(size.getText()));
		
		mx = x;
		my = y;
		
	}

}
