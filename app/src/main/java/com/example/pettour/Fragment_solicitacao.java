package com.example.pettour;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Fragment_solicitacao extends Fragment implements OnMapReadyCallback {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private MapView mapView;
    private EditText minutosEditText;
    private Button btnAdicionarMinutos;
    private Button btnSubtrairMinutos;
    private int minutos = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_solicitacao, container, false);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        mapView = rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Encontre os elementos do layout
        minutosEditText = rootView.findViewById(R.id.minutosEditText);
        btnAdicionarMinutos = rootView.findViewById(R.id.btnAdicionarMinutos);
        btnSubtrairMinutos = rootView.findViewById(R.id.btnSubtrairMinutos);

        // Configurar os listeners de clique para os botões
        btnAdicionarMinutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minutos++;
                atualizarTextoMinutos();
            }
        });

        btnSubtrairMinutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minutos > 0) {
                    minutos--;
                }
                atualizarTextoMinutos();
            }
        });

        // Resto do seu código

        return rootView;
    }

    private void atualizarTextoMinutos() {
        if (minutos <= 1) {
            minutosEditText.setText(minutos + " minuto");
        } else {
            minutosEditText.setText(minutos + " minutos");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Verifica a permissão de localização
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Solicite a permissão em tempo de execução
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            // Se a permissão já estiver concedida, exiba a localização atual no mapa
            showCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, exiba a localização atual no mapa
                showCurrentLocation();
            } else {
                // Permissão negada, você pode lidar com isso conforme necessário
            }
        }
    }

    private void showCurrentLocation() {
        // Obtém a localização atual
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Solicite a permissão em tempo de execução, por exemplo, mostrando um diálogo de solicitação
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        LatLng currentLatLng = new LatLng(latitude, longitude);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                        googleMap.addMarker(new MarkerOptions().position(currentLatLng).title("Minha Localização"));
                    }
                });
    }
}