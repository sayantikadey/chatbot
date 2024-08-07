import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.util.Calendar;
import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;


public class Server  implements ActionListener
{
    JTextField  text;
    JPanel a1;
    static Box verticle = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    Server()
    {
        f.setLayout(null);

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(7,94,84));
        panel1.setBounds(0,0,450,70);
        panel1.setLayout(null);
        f.add(panel1);

        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        panel1.add(back);

        back.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent ae)
            {
                System.exit(0);
            }
        });

        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel dp = new JLabel(i6);
        dp.setBounds(40,10,50,50);
        panel1.add(dp);

        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel vc = new JLabel(i9);
        vc.setBounds(300,20,25,25);
        panel1.add(vc);

        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel call = new JLabel(i12);
        call.setBounds(350,20,25,25);
        panel1.add(call);

        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(400,20,10,25);
        panel1.add(morevert);

        JLabel name = new JLabel("Sayantika");
        name.setBounds(110,15,100,25);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD, 18));
        panel1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110,30,100,25);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD, 12));
        panel1.add(status);

        a1 = new JPanel();
        a1.setBounds(0,70,449,650);
        f.add(a1);

        text = new JTextField();
        text.setBounds(8,740,300,40);
        text.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320,740,120,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SARIF", Font.BOLD, 16));
        f.add(send);

        f.setSize(450, 800);
        f.setLocation(300, 10);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        try{

        String output = text.getText();
        //System.out.println(output);
        JPanel panel2 = formatLable(output);
        //panel2.add(output1);
        a1.setLayout(new BorderLayout());

        JPanel right = new JPanel(new BorderLayout());
        right.add(panel2, BorderLayout.LINE_END);
        verticle.add(right);
        verticle.add(Box.createVerticalStrut(15));

        a1.add(verticle, BorderLayout.PAGE_START);

        text.setText("");

        dout.writeUTF(output);
        f.repaint();
        f.invalidate();
        f.validate();
        }
        catch(Exception exc)
        {
            exc.printStackTrace();
        }
        //throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    public static JPanel formatLable( String output)
    {
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

        JLabel output1 = new JLabel("<html><p style=\" width: 150px\">" + output + "</p></html>");
        output1.setFont(new Font("Tahoma",Font.PLAIN,16));
        output1.setBackground(Color.DARK_GRAY);
        output1.setOpaque(true);
        output1.setForeground(Color.WHITE);
        output1.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel3.add(output1);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(date.format(cal.getTime()));
        panel3.add(time);
        return panel3;
    }
    public static void main(String[] args) 
    {
        new Server();

        try{
            ServerSocket skt = new ServerSocket(6001);
            while (true) 
            {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true) 
                {
                    String msg = din.readUTF();
                    JPanel panel4 = formatLable(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel4, BorderLayout.LINE_START);
                    verticle.add(left);
                    f.validate();
                }
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}