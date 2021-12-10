package be.ehb.filesdemo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.squareup.picasso.Picasso;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import be.ehb.filesdemo.model.OurObject;

public class FirstFragment extends Fragment {

    private static final String FILE_NAME = "myfile.txt";
    private Context mContext;

    //verwijzingen in UI
    private Button btnInput;
    private Button btnOutput;
    private TextView tvInput;
    private EditText etOutput;
    private ImageView ivProfile;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnInput = view.findViewById(R.id.btn_input);
        btnOutput = view.findViewById(R.id.btn_output);
        tvInput = view.findViewById(R.id.tv_input);
        etOutput = view.findViewById(R.id.et_output);
        ivProfile = view.findViewById(R.id.iv_profiel);

        Picasso.get()
                .load(R.drawable.image)
                .resize(200,200)
                .centerInside()
                .into(ivProfile);


        btnOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //waar staat de file
                    FileOutputStream mFileOutputStream = mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    //wat sturen we door, een object
                    ObjectOutputStream mObjectOutputStream = new ObjectOutputStream(mFileOutputStream); //wrapper class

                    OurObject toWrite = new OurObject(etOutput.getText().toString());

                    //effectief opslaan
                    mObjectOutputStream.writeObject(toWrite);
                    //klaar met opslaan, verbinding mag dicht -> geen memory leaks
                    mObjectOutputStream.close();
                    //elke fout met invoer en uitvoer opvangen
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "error writing to file", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnInput.setOnClickListener( (View v) -> {
            try {
                FileInputStream mFileInputStream = mContext.openFileInput(FILE_NAME);
                ObjectInputStream mObjectInputStream = new ObjectInputStream(mFileInputStream);

                OurObject toRead = (OurObject) mObjectInputStream.readObject();

                tvInput.setText(toRead.getData());

                mObjectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "error reading file", Toast.LENGTH_LONG).show();
            } catch (ClassNotFoundException e) {//moest de klasse data bevatten die in een andere klasse/versie is geschreven
                e.printStackTrace();
                Toast.makeText(getActivity(), "error with file version", Toast.LENGTH_LONG).show();
            }
        });
    }
}