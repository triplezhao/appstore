package com.potato.library.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cyou.model.common.R;


/**
 * 个性化dialog的工具类，模板满足不了的，添加一个创建自定义dialog方法吧。
 */
public class DialogUtil {
    
    public interface CustomDialogCallBack {
        void OkClick();
        void CancelClick();
    }
    
    
    public static Dialog createCommonDialog(Context context,
            final CustomDialogCallBack callback,String title,String content,String text1,String text2) {
        
        final TempDialog dialog = new TempDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lib_dialog, null,
                false);
        TextView tv_title = (TextView)view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView)view.findViewById(R.id.tv_content);
        TextView sure = (TextView)view.findViewById(R.id.tv_sure);
        TextView cancel = (TextView)view.findViewById(R.id.tv_cancel);

	    if (!TextUtils.isEmpty(title)) {
		    tv_title.setText(title);
			tv_title.setVisibility(View.VISIBLE);
	    } else {
		    tv_title.setVisibility(View.GONE);
	    }
        tv_content.setText(content);
        sure.setText(text1);
        cancel.setText(text2);
        sure.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                callback.OkClick();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                callback.CancelClick();
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        
        return dialog;
    }
    
    public static Dialog createProgressDialog(Context context){
//        ProgressDialog progress = new ProgressDialog(context);
        final TempDialog progress = new TempDialog(context);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setContentView(new ProgressBar(context));
        progress.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.color.background_material_dark));
        
        return progress;
    }
    
    

}

class TempDialog extends Dialog {

    private Context context;

    public TempDialog(Context context) {
        super(context);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setWindow();
        this.setCancelable(true);
    }

    private void setWindow() {
        LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (MainApplication.screenHight * 0.5);
//        p.width = (int) (MainApplication.screenWidth * 0.9);
        // // p.x =0;//这两句设置了对话框的位置．0为中间
        // // p.y =180;
//         p.alpha = 1f;
         getWindow().setAttributes(p); // 设置生效
        getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.color.background_material_dark));
    }
}
