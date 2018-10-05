package ssuncake.simplestopwatch;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_record;
    Button btn_stop;
    Button btn_start;
    Button btn_pause;
    TextView tv_time;
    TextView tv_recordView;

    private Thread thread = null;
    private String record = "";
    private Boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_time = findViewById(R.id.tv_time);
        if(tv_time != null) {
            tv_time.setText(R.string.default_time);
        }

        tv_recordView = findViewById(R.id.tv_recordView);
        if(tv_recordView != null){
            tv_recordView.setText("");
        }

        btn_stop = findViewById(R.id.btn_stop);
        if (btn_stop != null){
            btn_stop.setOnClickListener(this);
        }

        btn_start = findViewById(R.id.btn_start);
        if (btn_start != null) {
            btn_start.setOnClickListener(this);
        }

        btn_record = findViewById(R.id.btn_record);
        if(btn_record != null){
            btn_record.setOnClickListener(this);
        }

        btn_pause = findViewById(R.id.btn_pause);
        if(btn_pause != null){
            btn_pause.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_pause:
                onClickPause();
                break;
            case R.id.btn_record:
                onClickRecord();
                break;
            case R.id.btn_start:
                onClickStart(v);
                break;
            case R.id.btn_stop:
                onClickStop(v);
                break;
        }
    }

    private void onClickStop(View v) {
        v.setVisibility(View.GONE);
        btn_record.setVisibility(View.GONE);
        btn_start.setVisibility(View.VISIBLE);
        btn_pause.setVisibility(View.GONE);

        isRunning = false;

        thread.interrupt();
        record="";
        tv_recordView.setText(record);
    }

    private void onClickStart(View v) {
        v.setVisibility(View.GONE);
        btn_stop.setVisibility(View.VISIBLE);
        btn_record.setVisibility(View.VISIBLE);
        btn_pause.setVisibility(View.VISIBLE);
        btn_pause.setText(R.string.pause);

        isRunning = true;

        thread = new Thread(new timeThread());
        thread.start();
    }

    private void onClickRecord() {
        record+=String.valueOf(tv_time.getText())+"\n";
        tv_recordView.setText(record);
    }

    private void onClickPause() {
        isRunning = !isRunning;
        if (isRunning){
            btn_pause.setText(R.string.pause);
        }else{
            btn_pause.setText(R.string.con);
        }
    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            int mSec = msg.arg1 % 100;
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
//            int hour = (msg.arg1 % 3600 ) % 24;
            String result = String.format(getString(R.string.time_set), min,sec,mSec);
            tv_time.setText(result);
        }
    };

    public class timeThread implements Runnable {
        @Override
        public void run() {
            int i = 0;
            while(true){
                while(isRunning) {
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }
}

