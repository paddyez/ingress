function [A, deg] = area(points,eps) % Berechnet die Fläche einer Dreiecks auf der Einheitskugel 
                                    % gegeben durch 'points' in Kugelkoordinaten (Längen- und Breitengraden)
deg = [0,0,0];
  for i=1:3
    % Berechnung der Tangente in A Richtung B
    err = 1;                          % Berechneter error zwischen den beiden Schritten, initial 1(möglichst hoch)
    
    point = K(points(:,i));           % Transformation des i-ten Vektors(A) aus Kugelkoordinaten in euklidische
    rest = points(:,[1:i-1,i+1:end]); % Spalte i(i-ter Vektor) rausgestrichen
    kp1 = K(rest(:,1));               % Transformation von B
    kp2 = K(rest(:,2));               % Transformation von C

    s = 0.1;                          % Anteil der Strecke zwischen A und B (Variable für f), hier 10%
    ff = f(point,kp1,s);              % Punkt zwischen A und B
    v1 = ff/norm(ff);                 % Projektion von der direkten Strecke auf die Kugel
    v2 = [0;0;0];                     % v2 initialisiert
    
    while err > eps         % Abbruchbedingung falls gewünschte Genauigkeit erreicht
      s = s/2;              
      
      ff = f(point,kp1,s);  % Berechnung des neuen Punktes näher an A
      v2 = ff/norm(ff);     % Projektion auf Kugel
      
      dv = v1-v2;           % Differenz der beiden Punkte nahe A
      dv = dv/norm(dv);     % Normiert
      
      err = norm(v2-v1);    % "Fehler" zwischen den beiden Berechnungsschritten
      v1 = v2;              % Überschreiben für den nächsten Iterationsschritt
    endwhile
    
    % Das selbe mit A und C
    err = 1;
    
    ff = f(point,kp2,0.1);
    w1 = ff/norm(ff);
    w2 = [0;0;0];
    s = 0.05;
    
    while err > eps
      s = s/2;
      
      ff = f(point,kp2,s);
      w2 = ff/norm(ff);
      
      dw = w1-w2;
      dw = dw/norm(dw);
      
      err = norm(w2-w1);
      w1 = w2;
    endwhile
    
    % Berechnung vom Winkel zwischen den beiden Tangenten
    dotp = dot(dv,dw)
    norms = (norm(dv)*norm(dw))
    deg(i) = acos(dotp/norms);
  endfor
  A = sum(deg) - pi;  % Winkelüberschuss oder so
end