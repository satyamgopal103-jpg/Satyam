import { Router, Request, Response } from 'express';

const router = Router();

router.get('/', (req: Request, res: Response) => {
  res.json({
    projects: [
      {
        id: "prj-01",
        title: "Green Corridor & Pedestrian Walkway",
        budget: "$1,250,000",
        progressPercent: 78,
        department: "Urban Planning & Forestry"
      },
      {
        id: "prj-02",
        title: "Smart Stormwater Drainage Overhaul",
        budget: "$2,800,000",
        progressPercent: 55,
        department: "Civic Infrastructure Corp"
      }
    ]
  });
});

export default router;
