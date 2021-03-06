package org.usfirst.frc.team1700.robot.motionprofiling.path;

import java.util.ArrayList;
import org.usfirst.frc.team1700.robot.motionprofiling.path.Waypoint;
import org.usfirst.frc.team1700.robot.motionprofiling.path.QuinticHermiteSpline;

public class GeneratePath{
    public GeneratePath(){
    }
    
    public ArrayList<PathPoint> generatePath(Waypoint waypoint1, Waypoint waypoint2) {
        ArrayList<PathPoint> path = new ArrayList<PathPoint>();
        double numPointsPerSegment = 100000.0;
        double posAlongPath = 0;
        Waypoint[] points = new Waypoint[] {
            waypoint1,
            waypoint2
        };

        double maxVelocity = 0;
    
        //List<QuinticHermiteSpline> splines = new ArrayList<>(waypoints.size() - 1);
        for (int i = 1; i < points.length; ++i) {
            System.out.println("Segment: " + i);
               
            double x0 = points[i-1].x;
            double y0 = points[i-1].y;
            double theta0 = points[i-1].angle;
            double x1 = points[i].x;
            double y1 = points[i].y;
            double theta1 = points[i].angle;
    
            QuinticHermiteSpline spline = new QuinticHermiteSpline(x0, y0, theta0, x1, y1, theta1);
    
            for(int j = 0; j < numPointsPerSegment; j++){
               double percentPath = (double)j/numPointsPerSegment;
               double x = spline.getX( percentPath );
               double y = spline.getY( percentPath );
               double deltaPos = spline.getVelocity( percentPath ) / numPointsPerSegment;
               maxVelocity = Math.max(maxVelocity, deltaPos);
               posAlongPath += deltaPos; 
    
               double heading = spline.getHeading( percentPath );
               double curvature = spline.getCurvature( percentPath );
               double dcurvature = spline.getDCurvature( percentPath );
               PathPoint point = new PathPoint(x, y, posAlongPath, heading, curvature, dcurvature);
               path.add(point);
            }
            if(i == points.length){
                double percentPath = 1;
                double x = spline.getX( percentPath );
                double y = spline.getY( percentPath );
                double deltaPos = spline.getVelocity( percentPath ) / numPointsPerSegment;
                maxVelocity = Math.max(maxVelocity, deltaPos);
                posAlongPath += deltaPos; 
     
                double heading = spline.getHeading( percentPath );
                double curvature = spline.getCurvature( percentPath );
                double dcurvature = spline.getDCurvature( percentPath );
                PathPoint point = new PathPoint(x, y, posAlongPath, heading, curvature, dcurvature);
                path.add(point);
            }
        }
        return path; 
    }
}