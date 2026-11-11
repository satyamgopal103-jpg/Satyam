import { Router, Request, Response } from 'express';

const router = Router();

router.post('/query', (req: Request, res: Response) => {
  const { prompt } = req.body;
  if (!prompt) {
    return res.status(400).json({ error: 'Prompt is required' });
  }

  // Civic knowledge response logic
  return res.json({
    answer: `Under municipal governance guidelines, complaints regarding ${prompt} are surveyed within 48 business hours by the designated department under Citizen Charter SLAs.`,
    suggestedFollowUps: [
      "Check active SLA status",
      "Escalate to Ward Councillor",
      "File Right to Information query"
    ]
  });
});

export default router;
