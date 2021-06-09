package in.menukart.menukart.view.setting;

import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import in.menukart.menukart.R;

public class SupportActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnSupportCall, btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        btnSupportCall = findViewById(R.id.btn_support_call);
        btnSupportCall.setOnClickListener(this);
        btnSendEmail = findViewById(R.id.btn_send_email);
        btnSendEmail.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_support_call:
                Intent intentCall = new Intent(Intent.ACTION_DIAL);
                intentCall.setData(Uri.parse("tel:8380053000"));
                startActivity(intentCall);
                break;
            case R.id.btn_send_email:
                Intent intentEmail = new Intent(Intent.ACTION_SEND);
                intentEmail.setType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                intentEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.text_email_id)});
                intentEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                intentEmail.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
                startActivity(Intent.createChooser(intentEmail, "Send Email"));
                break;
            default:
        }

    }
}