import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AlarmClock extends JFrame {//JFrameを継承してるからAlarmClockで定義しなくてもよい？
    private JLabel timeLabel;
    private JLabel alarmLabel;
    private JButton setButton;
    private JButton resetButton;
    private Timer timer;
    private Alarm alarm;

    public AlarmClock() {
        // JFrameの設定
        setTitle("Alarm Clock");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new GridLayout(3, 1));

        // 時刻表示用のラベルを作成
        timeLabel = new JLabel("", JLabel.CENTER); //中心に設定
        add(timeLabel); //追加はフレーム？パネル？

        // アラーム設定用のラベルを作成
        alarmLabel = new JLabel("", JLabel.CENTER); //中心に設定
        add(alarmLabel); //追加はフレーム？パネル？おそらくJFrameの設定をメソッドの初めにしているのでフレームに追加していると思われる

        // アラーム設定ボタンとリセットボタンを作成
        setButton = new JButton("アラーム設定");
        resetButton = new JButton("アラーム初期化");
        JPanel buttonPanel = new JPanel(); //パネルの定義
        buttonPanel.setLayout(new FlowLayout()); //ここ何？レイアウト関係っぽい
        buttonPanel.add(setButton); //setButton追加
        buttonPanel.add(resetButton); //resetButton追加
        add(buttonPanel);//追加はフレーム？パネル？おそらくJFrameの設定をメソッドの初めにしているのでフレームに追加していると思われる

        // ボタンのアクションリスナーを設定
        setButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("アラーム時刻を設定してください(hh:mm:ss)");//入力フォームの作成。表示文字　JOptionPaneクラスはユーザーにメッセージを表示したり何かを入力してもらうためのダイアログウィンドウを表示する
                if (input != null) {//もしインプットされてないじゃない場合（nullではない場合）
                    alarm = new Alarm(input);//alarm 入力したアラーム時刻、内部クラスであるAlarmクラスを呼び出し
                    alarmLabel.setText("Alarm set for " + alarm.toString()); //最初のウィンドウにAlarm set for+セットした時間が追記される
                    //toString 数字を文字列に変換する
                }
            }
        });

        //リセットボタンの処理
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                alarm = null;//アラームを初期化
                alarmLabel.setText("");//画面表示を初期化
            }
        });

        // タイマーを作成
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTime(); // 時刻を更新するメソッド
                checkAlarm(); // アラームをチェックするメソッド
            }
        });

        // タイマーを開始
        timer.start();
    }

    // 時刻を更新するメソッド
    private void updateTime() {
        timeLabel.setText(new java.util.Date().toString());
    }

    // アラームをチェックする＆鳴らすメソッド
    private void checkAlarm() {
        if (alarm != null && alarm.isTime()) { //alarm情報を持ったisTimeメソッドの呼び出し。アラームが設定されている、かつ、時刻が設定時刻と同じ（isTime()でtrue）
            JOptionPane.showMessageDialog(null, "アラーム設定時刻になりました");
            alarm = null; //アラームが作動したので値をnullにする
            alarmLabel.setText("");
        }
    }

    // Alarmクラスを定義　インナークラス？
    private class Alarm {
        private int hour;
        private int minute;
        private int second;

        public Alarm(String time) { //ここでアラーム時間設定の時刻フォーマットを定義する
            String[] parts = time.split(":");
            this.hour = Integer.parseInt(parts[0]); //時間設定をparts配列の1番目に格納
            this.minute = Integer.parseInt(parts[1]); //分設定をparts配列の2番目にに格納
            this.second = Integer.parseInt(parts[2]); //秒設定をparts配列の3番目に格納
        }

        public boolean isTime() { //アラームが鳴るべき時間かどうかを判定するメソッド,alarm情報を持ったisTimeメソッドの呼び出し
            java.util.Calendar now = java.util.Calendar.getInstance(); //現在時刻を取得
            int currentHour = now.get(java.util.Calendar.HOUR_OF_DAY);//それぞれ時分秒で格納
            int currentMinute = now.get(java.util.Calendar.MINUTE);
            int currentSecond = now.get(java.util.Calendar.SECOND);
            return (currentHour == hour && currentMinute == minute && currentSecond == second);//現在時刻との比較、一致すればtrue、一致しなければfalseを返す
        }

        public String toString() { //アラームの設定時間を文字列として返すメソッド
            return String.format("%02d:%02d:%02d", hour, minute, second);
        }
    }

    public static void main(String[] args) {
        new AlarmClock().setVisible(true); //AlarmClockのインスタンス化　.setVisible(true);は可視化のための一般的なコード
    }
}
