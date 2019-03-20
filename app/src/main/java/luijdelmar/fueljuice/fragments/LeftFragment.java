package luijdelmar.fueljuice.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import luijdelmar.fueljuice.R;

public class LeftFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "1";
    private TextView tvInfo;
    private TextView tvResult;
    private Button btnDownload;

    public LeftFragment() {
    }

    public static LeftFragment newInstance(int sectionNumber) {
        LeftFragment fragment = new LeftFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_left, container, false);

        tvInfo = rootView.findViewById(R.id.tv_title);
        tvResult = rootView.findViewById(R.id.tv_body);
        btnDownload = rootView.findViewById(R.id.btn_download);

        // FAB
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadWebsite();
            }
        });

        // To point of no return
        return rootView;
    } // End of onCreateView

    void downloadWebsite() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder bodyBuilder = new StringBuilder();
                final StringBuilder titleBuilder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("http://www.nafta.org").get();
                    String tNode = doc.title();
                    titleBuilder.append(tNode);
                    Elements bNodes = doc.getElementsByTag("tr");
                    for (Element node : bNodes) {
                        bodyBuilder.append(node.text()).append("\n");
                    }
                } catch (IOException e) {
                    bodyBuilder.append("Error: ").append(e.getMessage()).append("\n");
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String info = titleBuilder.toString().substring(0, 13);
                        String body = bodyBuilder.toString().replaceAll("Naziv Benzinska Cijena", "");
                        tvInfo.setText(info);
                        tvResult.setText(body);
                    }
                });
            }
        }).start();
    }
}
