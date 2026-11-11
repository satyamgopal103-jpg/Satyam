import { Router, Request, Response } from 'express';

const router = Router();

const sampleComplaints = [
  {
    id: "cmp-101",
    trackingId: "CP-2026-8491",
    title: "Severe Potholes on 5th Main Avenue",
    category: "ROADS",
    location: "5th Main Ave, Ward 4",
    status: "IN_PROGRESS",
    submittedDate: "2026-11-02",
    department: "Public Works & Highways",
    upvotes: 42
  },
  {
    id: "cmp-102",
    trackingId: "CP-2026-8492",
    title: "Broken Streetlight at Maple Park Gate",
    category: "LIGHTING",
    location: "Maple Park West Entrance",
    status: "RESOLVED",
    submittedDate: "2026-10-28",
    department: "Municipal Electrical Board",
    upvotes: 19
  }
];

router.get('/', (req: Request, res: Response) => {
  res.json({ complaints: sampleComplaints });
});

router.post('/', (req: Request, res: Response) => {
  const { title, description, category, location } = req.body;
  if (!title || !description || !category || !location) {
    return res.status(400).json({ error: 'All fields are mandatory' });
  }

  const randSuffix = Math.floor(1000 + Math.random() * 9000);
  const newComplaint = {
    id: `cmp-${randSuffix}`,
    trackingId: `CP-2026-${randSuffix}`,
    title,
    description,
    category,
    location,
    status: 'SUBMITTED',
    submittedDate: new Date().toISOString().split('T')[0],
    department: 'Assigned Municipal Directorate',
    upvotes: 1
  };

  return res.status(201).json(newComplaint);
});

router.patch('/:id/status', (req: Request, res: Response) => {
  const { status, note } = req.body;
  return res.json({
    message: 'Complaint status updated',
    id: req.params.id,
    newStatus: status,
    officialNote: note,
    updatedAt: new Date().toISOString()
  });
});

export default router;
