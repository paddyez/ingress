% Dreieck mit 3mal 90°
P1 = [0;0]        % Nordpol
P2 = [pi/2;0]     % Äquator 1
P3 = [pi/2;pi/2]  % Äquator 2

[A, angle_rad] = area([P1,P2,P3],1e-5)

angle_deg = rad2deg(angle_rad)