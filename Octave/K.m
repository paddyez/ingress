function M = K(v)         % Berechnet die Transformation von Kugel- in euklidische Koordinaten
  x = sin(v(1))*cos(v(2));
  y = sin(v(1))*sin(v(2));
  z = cos(v(1));
  M = [x;y;z];
  end