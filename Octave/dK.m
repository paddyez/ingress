function M = dK(v)
  x1 = cos(v(1))*cos(v(2));
  x2 = -sin(v(1))*sin(v(2));
  y1 = cos(v(1))*sin(v(2));
  y2 = sin(v(1))*cos(v(2));
  z1 = -sin(v(1));
  z2 = 0;
  M = [x1,x2;y1,y2;z1,z2];
  end