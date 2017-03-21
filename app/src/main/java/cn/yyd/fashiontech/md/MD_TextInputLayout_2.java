package cn.yyd.fashiontech.md;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import cn.yyd.fashiontech.R;

public class MD_TextInputLayout_2 extends AppCompatActivity {

    TextInputLayout mUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md__text_input_layout_2);
        mUserName = (TextInputLayout) findViewById(R.id.txt_layout_userName);
        mUserName.setCounterEnabled(true);
        mUserName.setCounterMaxLength(6);
        mUserName.setPasswordVisibilityToggleEnabled(true);
        mUserName.setPasswordVisibilityToggleDrawable(R.drawable.ic_red_circle);
        mUserName.getEditText().setTransformationMethod(PlusTransformationMethod.getInstance());
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("123".equals(mUserName.getEditText().getText().toString())){

                }else {
                    mUserName.setErrorEnabled(true);
                    mUserName.setError("input 123");

                }
            }
        });
    }
    static class PlusTransformationMethod extends PasswordTransformationMethod{
        public static PasswordTransformationMethod getInstance() {
            if (sInstance != null)
                return sInstance;

            sInstance = new PlusTransformationMethod();
            return sInstance;
        }

        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PlusCharSequence(source);
        }

        private static PlusTransformationMethod sInstance;
    }
    static class PlusCharSequence implements CharSequence{

        PlusCharSequence(CharSequence source){
            mSource = source;
        }

        private static char PLUS = '+';
        private CharSequence mSource;

        @Override
        public int length() {
            return mSource.length();
        }

        @Override
        public char charAt(int index) {
            return PLUS;
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            char[] buf = new char[end - start];

            getChars(start, end, buf, 0);
            return new String(buf);
        }


        public void getChars(int start, int end, char[] dest, int off) {
            TextUtils.getChars(mSource, start, end, dest, off);

            int st = -1, en = -1;
            int nvisible = 0;
            int[] starts = null, ends = null;

//            if (mSource instanceof Spanned) {
//                Spanned sp = (Spanned) mSource;
//
//                st = sp.getSpanStart(TextKeyListener.ACTIVE);
//                en = sp.getSpanEnd(TextKeyListener.ACTIVE);
//
//                Visible[] visible = sp.getSpans(0, sp.length(), Visible.class);
//                nvisible = visible.length;
//                starts = new int[nvisible];
//                ends = new int[nvisible];
//
//                for (int i = 0; i < nvisible; i++) {
//                    if (sp.getSpanStart(visible[i].mTransformer) >= 0) {
//                        starts[i] = sp.getSpanStart(visible[i]);
//                        ends[i] = sp.getSpanEnd(visible[i]);
//                    }
//                }
//            }

            for (int i = start; i < end; i++) {
                if (! (i >= st && i < en)) {
                    boolean visible = false;

                    for (int a = 0; a < nvisible; a++) {
                        if (i >= starts[a] && i < ends[a]) {
                            visible = true;
                            break;
                        }
                    }

                    if (!visible) {
                        dest[i - start + off] = PLUS;
                    }
                }
            }
        }
    }
}
