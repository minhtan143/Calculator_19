package hcmute.edu.vn.calculator_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtScreen;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnAdd, btnSubtract, btnMultiply, btnDivide, btnDot, btnDelete, btnEqual;
    String output, presentNum, tempOperation;
    Boolean isOperation, isFirstCalculation, isEqual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isOperation = false;
        isFirstCalculation = true;
        isEqual = false;
        tempOperation = "";
        output = "";
        presentNum = "";
        txtScreen = findViewById(R.id.txtScreen);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        btnDot = findViewById(R.id.btnDot);
        btnDelete = findViewById(R.id.btnDelete);
        btnEqual = findViewById(R.id.btnEqual);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSubtract.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn0:
                clickNumber("0");
                break;
            case R.id.btn1:
                clickNumber("1");
                break;
            case R.id.btn2:
                clickNumber("2");
                break;
            case R.id.btn3:
                clickNumber("3");
                break;
            case R.id.btn4:
                clickNumber("4");
                break;
            case R.id.btn5:
                clickNumber("5");
                break;
            case R.id.btn6:
                clickNumber("6");
                break;
            case R.id.btn7:
                clickNumber("7");
                break;
            case R.id.btn8:
                clickNumber("8");
                break;
            case R.id.btn9:
                clickNumber("9");
                break;
            case R.id.btnDot:
                clickDot();
                break;
            case R.id.btnAdd:
                clickOperation("+");
                break;
            case R.id.btnSubtract:
                clickOperation("-");
                break;
            case R.id.btnDivide:
                clickOperation("/");
                break;
            case R.id.btnMultiply:
                clickOperation("*");
                break;
            case R.id.btnEqual:
                getResult();
                isEqual = true;
                break;
            case R.id.btnDelete:
                reset();
                break;
        }
    }

    public void clickNumber(String a){
        if (isOperation){
            output += tempOperation + a;
            presentNum = a;
        }
        else if (isEqual){
            isFirstCalculation = true;
            isEqual = false;
            output = a;
            presentNum = a;
        }
        else {
            output += a;
            presentNum += a;
        }
        isOperation = false;
        txtScreen.setText(presentNum);
    }

    public void clickOperation(String a){
        isOperation = true;
        getResult();
        tempOperation = " " + a + " ";
    }

    public void clickDot(){
        if (!presentNum.contains(".")){
            if (isOperation){
                output += tempOperation + ".";
                presentNum = ".";
                isOperation = false;
            }
            else {
                output += ".";
                presentNum += ".";
            }
            txtScreen.setText(presentNum);
        }
    }

    public void getResult(){
        Double result = 0d;

        output.trim();
        String[] elementMath = output.split(" ");
        if (isFirstCalculation || isEqual) {
            result = Double.parseDouble(elementMath[0]);
            isFirstCalculation = false;
            isEqual = false;
        }
        else {
            Double num1 = Double.parseDouble(elementMath[0]);
            Double num2 = Double.parseDouble(elementMath[2]);
            switch (elementMath[1]) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    result = num1 / num2;
                    break;
            }
        }

        output = result.toString();
        if (isInteger(result)) {
            Integer temp = result.intValue();
            txtScreen.setText(temp.toString());
        }
        else
            txtScreen.setText(result.toString());
    }

    public boolean isInteger(Double a){
        Integer b = a.intValue();
        if (a - b == 0)
            return true;
        else
            return false;
    }

    public void reset(){
        isOperation = false;
        isFirstCalculation = true;
        tempOperation = "";
        output = "";
        presentNum = "";
        txtScreen.setText("0");
    }
}
