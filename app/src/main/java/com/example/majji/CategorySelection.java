package com.example.majji;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class CategorySelection extends AppCompatActivity {

    Spinner spinner_sex, spinner_top_bottom_choice, spinner_top_length, spinner_bottom_length,
            spinner_details_one, spinner_details_two;
    TextView tv_sex_choice, tv_sex_result, tv_top_bottom_choice, tv_top_bottom_result, tv_length_choice, tv_length_result,
            tv_details_one_choice, tv_details_one_result, tv_details_two_choice, tv_details_two_result;
    Button btn_search;


    //String sex_result, top_bottom_result, sleeve_result, neck_result, patten_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        //ArrayAdapter<CharSequence> sex_adapter;
        //ArrayAdapter<CharSequence> top_pants_adapter;
        //ArrayAdapter<CharSequence> top_pants_type_choice_adapter;
        //ArrayAdapter<CharSequence> bottom_type_choice_adapter;


        tv_sex_choice = (TextView) findViewById(R.id.tv_sex_choice);
        spinner_sex = (Spinner) findViewById(R.id.spinner_sex);
        tv_sex_result = (TextView) findViewById(R.id.tv_sex_result);


        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //첫 번째 성별 선택 옵션(default : None)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_sex_result.setText(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tv_sex_result.setText("None");
            }
        });


        tv_top_bottom_choice = (TextView) findViewById(R.id.tv_Top_Pants_choice);
        spinner_top_bottom_choice = (Spinner) findViewById(R.id.spinner_Top_Bottom_Choice);
        tv_top_bottom_result = (TextView) findViewById(R.id.tv_top_bottom_result);

        spinner_top_bottom_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //두 번째 선택 옵션(default : None)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tv_top_bottom_result.setText(parent.getItemAtPosition(position).toString());

                if(spinner_top_bottom_choice.getSelectedItem().toString().equals("Top")) {
                    //Top 선택 시 하위 옵션인 길이 옵션에서 long_top, short_top, None 선택 가능
                    //Bottom 옵션도 선택은 가능하지만 선택해도 값이 다음 액티비티로 넘겨지지는 않음

                    tv_length_choice = (TextView) findViewById(R.id.tv_top_Bottom_Length_Choice);
                    spinner_top_length = (Spinner) findViewById(R.id.spinner_Length_Top);
                    tv_length_result = (TextView) findViewById(R.id.tv_top_Bottom_Length_Result);


                    tv_details_one_choice = (TextView) findViewById(R.id.tv_More_Details_One_Choice);
                    spinner_details_one = (Spinner) findViewById(R.id.spinner_more_details_one_top);
                    tv_details_one_result = (TextView) findViewById(R.id.tv_More_Details_One_Result);


                    tv_details_two_choice = (TextView) findViewById(R.id.tv_More_Details_Two_Choice);
                    spinner_details_two = (Spinner) findViewById(R.id.spinner_more_details_two_top);
                    tv_details_two_result = (TextView) findViewById(R.id.tv_More_Details_Two_Result);


                    spinner_top_length.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tv_length_result.setText(parent.getItemAtPosition(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            tv_length_result.setText("None");
                            tv_details_one_result.setText("None");
                            tv_details_two_result.setText("None");

                        }
                    });

                    spinner_details_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tv_details_one_result.setText(parent.getItemAtPosition(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            tv_details_one_result.setText("None");
                        }
                    });

                    spinner_details_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tv_details_two_result.setText(parent.getItemAtPosition(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            tv_details_two_result.setText("None");
                        }
                    });

                }

                else if(spinner_top_bottom_choice.getSelectedItem().toString().equals("Bottom")) {
                    //Bottom 선택 시 하위 옵션인 길이 옵션에서 long_bottom, short_bottom, None 선택 가능
                    //Top 옵션도 선택은 가능하지만 선택해도 값이 다음 액티비티로 넘겨지지는 않음
                    tv_length_choice = (TextView) findViewById(R.id.tv_top_Bottom_Length_Choice);
                    spinner_bottom_length = (Spinner) findViewById(R.id.spinner_Length_Bottom);
                    tv_length_result = (TextView) findViewById(R.id.tv_top_Bottom_Length_Result);

                    tv_details_one_choice = (TextView) findViewById(R.id.tv_More_Details_One_Choice);
                    spinner_details_one = (Spinner) findViewById(R.id.spinner_more_details_one_bottom);
                    tv_details_one_result = (TextView) findViewById(R.id.tv_More_Details_One_Result);

                    tv_details_two_choice = (TextView) findViewById(R.id.tv_More_Details_Two_Choice);
                    spinner_details_two = (Spinner) findViewById(R.id.spinner_more_details_two_bottom);
                    tv_details_two_result = (TextView) findViewById(R.id.tv_More_Details_Two_Result);

                    spinner_bottom_length.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tv_length_result.setText(parent.getItemAtPosition(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            tv_length_result.setText("None");
                            tv_details_one_result.setText("None");
                            tv_details_two_result.setText("None");
                        }
                    });

                    spinner_details_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tv_details_one_result.setText(parent.getItemAtPosition(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            tv_details_one_result.setText("None");
                        }
                    });

                    spinner_details_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tv_details_two_result.setText(parent.getItemAtPosition(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            tv_details_two_result.setText("None");
                        }
                    });
                }

                else {
                    //None 선택할 경우 다음 액티비티에 None 값이 전달됨
                    tv_length_choice = (TextView) findViewById(R.id.tv_top_Bottom_Length_Choice);
                    spinner_top_length = (Spinner) findViewById(R.id.spinner_Length_Top);
                    tv_length_result = (TextView) findViewById(R.id.tv_top_Bottom_Length_Result);

                    tv_details_one_choice = (TextView) findViewById(R.id.tv_More_Details_One_Choice);
                    spinner_details_one = (Spinner) findViewById(R.id.spinner_more_details_one_top);
                    tv_details_one_result = (TextView) findViewById(R.id.tv_More_Details_One_Result);

                    tv_details_two_choice = (TextView) findViewById(R.id.tv_More_Details_Two_Choice);
                    spinner_details_two = (Spinner) findViewById(R.id.spinner_more_details_two_top);
                    tv_details_two_result = (TextView) findViewById(R.id.tv_More_Details_Two_Result);

                    tv_length_result.setText("None");
                    tv_details_one_result.setText("None");
                    tv_details_two_result.setText("None");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tv_top_bottom_result.setText("None");
            }
        });


        btn_search = (Button) findViewById(R.id.btn_keyword);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FirstscreenActivity.class);
                intent.putExtra("sex_input", tv_sex_result.getText().toString());
                intent.putExtra("top_pants_input", tv_top_bottom_result.getText().toString());
                intent.putExtra("top_pants_type_input", tv_length_result.getText().toString());
                intent.putExtra("detail_one", tv_details_one_result.getText().toString());
                intent.putExtra("detail_two", tv_details_two_result.getText().toString());
                startActivity(intent);
            }
        });


    }
}