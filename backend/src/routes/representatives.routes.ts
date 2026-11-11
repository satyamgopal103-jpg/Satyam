import { Router, Request, Response } from 'express';

const router = Router();

router.get('/', (req: Request, res: Response) => {
  res.json({
    representatives: [
      {
        id: "rep-01",
        name: "Hon. Priya Sharma",
        title: "Member of Legislative Assembly (MLA)",
        constituency: "Metro North Constituency",
        responseRatePercent: 94,
        projectsCompleted: 18,
        grantUtilized: "$2.4M (92% of civic budget)"
      },
      {
        id: "rep-02",
        name: "Dr. Vikramaditya Rao",
        title: "Municipal Ward Councillor",
        constituency: "Ward 4 — Central",
        responseRatePercent: 89,
        projectsCompleted: 12,
        grantUtilized: "$850K (88% of ward fund)"
      }
    ]
  });
});

export default router;
