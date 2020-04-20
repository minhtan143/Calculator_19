package hcmute.edu.vn.calculator_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtScreen;
    String output, presentNum, tempOperation;
    Boolean isOperation, isNumber, isFirstCalculation, isEqual;
    Double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initParameter();
        restoreStateWhenRotate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()){
                case R.id.btn0: clickNumber("0"); break;
                case R.id.btn1: clickNumber("1"); break;
                case R.id.btn2: clickNumber("2"); break;
                case R.id.btn3: clickNumber("3"); break;
                case R.id.btn4: clickNumber("4"); break;
                case R.id.btn5: clickNumber("5"); break;
                case R.id.btn6: clickNumber("6"); break;
                case R.id.btn7: clickNumber("7"); break;
                case R.id.btn8: clickNumber("8"); break;
                case R.id.btn9: clickNumber("9"); break;
                case R.id.btnDot: clickDot(); break;
                case R.id.btnAdd: clickOperation("+"); break;
                case R.id.btnSubtract: clickOperation("-"); break;
                case R.id.btnDivide: clickOperation("/"); break;
                case R.id.btnMultiply: clickOperation("*"); break;
                case R.id.btnEqual: clickEqual(); break;
                case R.id.btnDelete: reset(); break;
            }
        }
        catch (Exception ex) {
            reset();
            txtScreen.setText("ERROR");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("result", result);
        outState.putString("output", output);
        outState.putString("presentNum", presentNum);
        outState.putString("tempOperation", tempOperation);
        outState.putBoolean("isOperation", isOperation);
        outState.putBoolean("isFirstCalculation", isFirstCalculation);
        outState.putBoolean("isEqual", isEqual);
        outState.putBoolean("isNumber", isNumber);
    }

    public void restoreStateWhenRotate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            result = savedInstanceState.getDouble("result");
            output = savedInstanceState.getString("output");
            presentNum = savedInstanceState.getString("presentNum");
            tempOperation = savedInstanceState.getString("tempOperation");
            isOperation = savedInstanceState.getBoolean("isOperation");
            isFirstCalculation = savedInstanceState.getBoolean("isFirstCalculation");
            isEqual = savedInstanceState.getBoolean("isEqual");
            isNumber = savedInstanceState.getBoolean("isNumber");

            if (isNumber)
                txtScreen.setText(presentNum);
            else processResult();
        }
    }

    public void initParameter() {
        result = 0d;
        isOperation = false;
        isNumber = false;
        isFirstCalculation = true;
        isEqual = false;
        tempOperation = "";
        output = "0";
        presentNum = "";
        txtScreen = findViewById(R.id.txtScreen);

        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnSubtract).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
        findViewById(R.id.btnEqual).setOnClickListener(this);
    }

    public void clickNumber(String a) {
        if (isOperation) {
            output += tempOperation + a;
            presentNum = a;
        }
        else if (isEqual) {
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
        isNumber = true;
        txtScreen.setText(presentNum);
    }

    public void clickOperation(String a) {
        tempOperation = " " + a + " ";
        if (!isOperation) {
            getResult();
        }
        isOperation = true;
        isNumber = false;
    }

    public void clickEqual() {
        getResult();
        isEqual = true;
        isNumber = false;
    }

    public void clickDot() {
        if (isOperation) {
            output += tempOperation + ".";
            presentNum = ".";
            isOperation = false;
        }
        else if (isEqual) {
            isFirstCalculation = true;
            isEqual = false;
            output = ".";
            presentNum = ".";
        }
        else if (!presentNum.contains(".")) {
            output += ".";
            presentNum += ".";
        }
        isNumber = true;
        txtScreen.setText(presentNum);
    }

    public void getResult() {
        result = 0d;
        BigDecimal temp;

        output.trim();
        String[] elementMath = output.split(" ");
        if (isFirstCalculation || isEqual) {
            result = Double.parseDouble(elementMath[0]);
            isFirstCalculation = false;
            isEqual = false;
        }
        else {
            BigDecimal num1 = new BigDecimal(elementMath[0]);
            BigDecimal num2 = new BigDecimal(elementMath[2]);
            switch (elementMath[1]) {
                case "+":
                    temp = num1.add(num2);
                    result = temp.doubleValue();
                    break;
                case "-":
                    temp = num1.subtract(num2);
                    result = temp.doubleValue();
                    break;
                case "*":
                    temp = num1.multiply(num2);
                    result = temp.doubleValue();
                    break;
                case "/":
                    if (num2.compareTo(new BigDecimal(0)) == 0) {
                        result = num1.doubleValue() / num2.doubleValue();
                    }
                    else {
                        temp = num1.divide(num2, MathContext.DECIMAL64);
                        result = temp.doubleValue();
                    }
                    break;
            }
        }
        processResult();
    }

    public void processResult() {
        output = result.toString();
        if (isInteger(result)) {
            Integer temp = result.intValue();
            txtScreen.setText(temp.toString());
        }
        else txtScreen.setText(result.toString());
    }

    public boolean isInteger(Double a) {
        if (a - a.intValue() == 0)
            return true;
        return false;
    }

    public void reset() {
        isOperation = false;
        isFirstCalculation = true;
        isNumber = false;
        tempOperation = "";
        output = "0";
        presentNum = "";
        txtScreen.setText("0");
    }
}
